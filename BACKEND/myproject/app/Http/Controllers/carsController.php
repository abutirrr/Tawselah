<?php

namespace App\Http\Controllers;

use App\Car;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;

class carsController extends Controller
{

    public function index()
    {
        return Response::json(Car::all(), 200);
    }


    public function show($id)
    {
        $car = Car::find($id);
        return Response::json($car, 200);
    }
}

