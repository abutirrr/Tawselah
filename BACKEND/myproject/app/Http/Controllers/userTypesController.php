<?php

namespace App\Http\Controllers;

use App\user_type;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Response;

class userTypesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Response::json(user_type::all(),200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $usertype = new user_type();
        $usertype->type_name = $request->type_name;
        $usertype->save();
        return Response::json($request,200);
    }

}
