<?php

namespace App;

use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;

class User extends Authenticatable
{
    protected $primaryKey='user_id';
    protected $table='users';

    public function rides(){
        return $this->belongsToMany(Rides::class,'user_ride');
    }
    public function drivers(){
        return $this->hasOne(Driver::class,'user_id');
    }
   // public $timestamps=false;
    protected $guarded = [

    ];
}
