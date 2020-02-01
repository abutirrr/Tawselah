<?php

namespace App\Http\Controllers;

use App\Rides;
use App\trip;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;

class ridesController extends Controller
{
 // fetch all rides
    public function showAllRides()
    {
        if(empty(Rides::all()))
            return Response::json('There were no rides created yet',404);
        else
            return Response::json(Rides::all(),200);
    }
    public function destroy($id)
    {
        if(empty(Rides::find($id)))
            return Response::json('there are no rides with this id',200);
        else
        {
            Rides::destroy($id);
            return Response::json('sucessfuly deleted the ride',200);
        }

    }
// fetch the ride with a particular id
    public function show($id)
    {
        $rides = Rides::find($id);
        if(empty($rides))
            return Response::json('there where no such ride with this id',404);
        else
            return Response::json($rides,200);
    }

}
