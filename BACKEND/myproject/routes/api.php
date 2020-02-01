<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
//users
Route::get('users/showAllUsers','usersController@showAllUsers');
Route::post('users/createAUser','usersController@createAUser');
Route::post('userLogin',"usersController@userLogin");
Route::post('users/becomeADriver','usersController@becomeADriver');
Route::post('users/searchForARide','usersController@searchForARide');
Route::post('users/joinARide','usersController@joinARide')->middleware('checkSeats');
Route::post('users/fetchJoinedTrips','usersController@fetchJoinedTrips');
Route::post('users/removeJoinedTrip','usersController@removeJoinedTrip');
Route::post('users/returnDriverId','usersController@returnDriverId');
Route::apiResource('users','usersController')
    ->only('show','update','destroy');
// drivers
Route::get('drivers/showAllDrivers','driversController@showAllDrivers');
Route::post('drivers/createARide','driversController@createARide');
Route::post('drivers/fetchCreatedRides','driversController@fetchCreatedRides');
Route::post('drivers/showPassengersInRide','driversController@showPassengersInRide');
Route::apiResource('drivers','driversController')
    ->only('show');
// rides
Route::get('rides/showAllRides','ridesController@showAllRides');
Route::apiResource('rides','ridesController')
    ->only('show','destroy');
// user_types
Route::apiResource('userTypes', 'userTypesController')
    ->only('index','store');
// cars
Route::apiResource('cars', 'carsController')
    ->only('show','index');
