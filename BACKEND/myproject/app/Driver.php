<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Driver extends Model
{

    protected $primaryKey='driver_id';
    protected $table='drivers';

    public function rides(){
        return $this->hasMany(Rides::class,'driver_id');
    }

    public function car(){
        return $this->belongsTo(Car::class,'car_id');
    }

    public function user(){
        return $this->belongsTo(user::class,'user_id');
    }

    public $timestamps=false;
    protected $guarded=[

    ];

}
