<?php


namespace App\Http\Controllers;

use App\Car;
use App\Driver;
use App\Rides;
use App\User;
use App\user_type;
use Illuminate\Http\Request;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Response;
use mysql_xdevapi\Exception;
use function Sodium\increment;

class usersController extends Controller
{

    public function showAllUsers()
    {
        if (empty(User::all()))
            return Response::json('There are no users to show', 200);
        else
            return Response::json(User::all(), 200);
    }

    public function createAUser(Request $request)
    {
        $user = User::where([
            'user_name' => $request->user_name
        ])->get();


        if ($user->isEmpty()) {
            $data = $request->validate([
                'user_name' => 'required',
                'email' => 'required',
                'password' => 'required',
                'phone_number' => 'required',
                'gender' => 'required',
                'date_of_birth' => 'required',
                'picture' => ''
            ]);
            User::create($data);

            return Response::json($data, 201);
        } else {
            return Response::json("User already exists", 404);
        }


    }

    public function userLogin(Request $request)
    {
        $requestUserName = $request->user_name;
        $requestPassword = $request->password;


        $user = User::where([
            'user_name' => $requestUserName
        ])->first();

        if (empty($requestPassword) or empty($requestUserName)) {
            return Response::json("Please Enter Username and Password", 204);
        } else if (empty($user)) {
            return Response::json("user not found", 205);
        } else {

            $password = $user->password;

            if ($password == $requestPassword) {
                return Response::json($user, 200);
            } else if ($password != $requestPassword) {
                return Response::json("password does not match", 204);
            }
        }

    }

    public function show($id)
    {

        $user = User::find($id);
        if (empty($user))
            return Response::json('user was no found', 200);
        else
            return Response::json($user, 200);
    }

    public function update(Request $request, $id)
    {

        $user = User::find($id);
        if (empty($user)) {
            return Response::json('there is no usGGGGGGer to be updated', 404);
        } else {

            $data = $request->validate([
                //   'user_name' => '',
                'email' => '',
                'password' => '',
                'phone_number' => '',
                //    'gender' => '',
                'date_of_birth' => '',
                //   'type_id' => '',
                // 'picture' => ''
            ]);

            $user->update($data);
            $afterUser = User::find($id);
            return Response::json($afterUser, 201);
        }
    }

    public function becomeADriver(Request $request)
    {

        DB::transaction(function () use ($request) {
            // car model insert
            $car = new Car();
            $car->car_model = $request->car_model;
            $car->car_license = $request->car_license;
            $car->save();

            //driver model insert
            $driver = new Driver();
            $driver->user_id = $request->user_id;
            $driver->car_id = $car->car_id;    // car_id was created in this transaction above
            $driver->save();

            // user model update
            $user = User::find($request->user_id);
            $user->type_id = 2;
            $user->save();
        });
        return Response::json($request, 200);

    }

    public function returnDriverId(Request $request)
    {
        $driver = Driver::where([
            'user_id' => $request->user_id
        ])->first();

        if (empty($driver)) {
            return Response::json("no users found", 405);
        } else {
            return Response::json($driver, 200);
        }

    }

    public function destroy($id)
    {
        $user = User::find($id);
        if (empty($user)) {
            return Response::json('user was not found', 404);
        } else {


            User::destroy($id);
            return Response::json('suncessfuly deleted user', 200);
        }
    }

    // ADD SEARCH VALIDATION TIME AND DATE AND DESTINATION CITY AND SOURCE CITY
    public function searchForARide(Request $request)
    {
        // this only gives the ride information

        $destination_city = $request->destination_city;
        $source_city = $request->source_city;


        if (!$destination_city && !$source_city) {
            // sample code
            //$books = App\Book::with(['author', 'publisher'])->get();
            $rides = Rides::with(['driver.car', 'driver.user'])
                ->where('ride_date', '>', Carbon::now())
                //->whereBetween('ride_date',Carbon::now())
                ->orderBy('ride_date')->orderBy('ride_time')->get();

            if (empty($rides)) {
                return Response::json('no rides where found', 404);
            } else {
                return Response::json($rides, 200);
            }
        } else if ($destination_city && $source_city) {

            $rides = Rides::with(['driver.car', 'driver.user'])->where([
                'destination_city' => $destination_city,
                'source_city' => $source_city,
            ])->where('ride_date', '>', Carbon::now())
                //->whereBetween('ride_date',Carbon::now())
                ->orderBy('ride_date')->orderBy('ride_time')->get();

            return Response::json($rides, 200);
        } else if (!$destination_city && $source_city) {
            $rides = Rides::with(['driver.car', 'driver.user'])->where([
                'source_city' => $source_city,
            ])->where('ride_date', '>', Carbon::now())
                //->whereBetween('ride_date',Carbon::now())
                ->orderByDesc('ride_date')->orderByDesc('ride_time')->get();

            return Response::json($rides, 200);
        } else if ($destination_city && !$source_city) {
            $rides = Rides::with(['driver.car', 'driver.user'])->where([
                'destination_city' => $destination_city,
            ])->where('ride_date', '>', Carbon::now())
                //->whereBetween('ride_date',Carbon::now())
                ->orderBy('ride_date')->orderBy('ride_time')->get();


            return Response::json($rides, 200);
        }
    }

    public function fetchRideSelected(Request $request)
    {

        $rides = Rides::with(['driver.car', 'driver.user'])->get();

        if (empty($rides))
            return Response::json('there where no such ride with this id', 404);
        else
            return Response::json($rides, 200);
    }

    public function joinARide(Request $request)
    {
        // i only need the ride id and the user_id to join a trip
        try {
            $user = User::find($request->user_id);
            $ride = Rides::find($request->ride_id);
            if (empty($user) or empty($ride)) {
                return Response::json('does not exist', 400);
            }
//            $user->rides()->attach($request->ride_id);
            $ride->users()->attach($user->user_id);
            $ride->increment('seats_booked', 1);
            return Response::json('sucessfuly joined', 200);

        } catch (\Exception $e) {
            return Response::json('unexpected error', 500);
        }
    }

    public function fetchJoinedTrips(Request $request)
    {

        $rides = User::find($request->user_id)->rides()->with(['driver.car', 'driver.user'])
            ->where('ride_date', '>', Carbon::now())
            //->whereBetween('ride_date',Carbon::now())
            ->orderBy('ride_date')->orderBy('ride_time')->get();


        if (empty($rides))
            return Response::json('no trips found', 200);
        else
            return Response::json($rides, 200);
    }

    public function removeJoinedTrip(Request $request)
    {

        $user = User::find($request->user_id);
        $ride = Rides::find($request->ride_id);
        if (empty($user) or empty($ride)) {
            return Response::json('incorrect data entered', 460);
        }
        $checkIfRideIsTrue = User::find($request->user_id)->rides()->where(['ride_id' => $request->ride_id])->get();

        if ($checkIfRideIsTrue->isEmpty()) {
            return Response::json('no such trip to be deleted', 466);
        }

        $user->rides()->detach($request->ride_id);
        $ride->decrement('seats_booked', 1);
        return Response::json('sucessfuly removed', 200);


    }


}
