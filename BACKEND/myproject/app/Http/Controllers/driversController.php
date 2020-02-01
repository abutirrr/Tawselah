<?php

namespace App\Http\Controllers;

use App\Driver;
use App\Rides;
use App\User;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;

class driversController extends Controller
{

    public function showAllDrivers()
    {
        if(empty(Driver::all()))
            return Response::json('there are no drivers', 404);
        else
            return Response::json(Driver::all(), 200);
    }

    public function show($id)
    {
        $driver = Driver::find($id);
        if(empty($driver))
            return Response::json('there is no driver with this id', 200);
        else
            return Response::json($driver, 200);
    }

    public function createARide(Request $request)
    {

        // create a rides
        $data = $request->validate([
            'driver_id' => 'required',
            'source_city' => 'required',
            'destination_city' => 'required',
            'source_pin' => 'required',
            'destination_pin' => 'required',
            'ride_time' => 'required',
            'ride_date' => 'required',
            'price' => 'required',
            'seats_available' => 'required',
        ]);
        Rides::create($data);

        return Response::json($data, 201);
    }

    public function fetchCreatedRides(Request $request)
    {
        $rides = Rides::with(['driver.car','driver.user'])->
        where(['driver_id'=>$request->driver_id])->
        orderBy('ride_date')->orderBy('ride_time')->get();
        return Response::json($rides,200);
    }

    public function showPassengersInRide(Request $request){

        $users = Rides::find($request->ride_id)->users()->get();
        return Response::json($users,200);
    }


}
