<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Rides extends Model
{
    protected $primaryKey = 'ride_id';
    protected $table = 'rides';

    // hide pivot table on response
    protected $hidden = ['pivot'];

    public function users()
    {
        return $this->belongsToMany(User::class, 'user_ride');
    }

    public function driver()
    {
        return $this->belongsTo(Driver::class, 'driver_id');
    }

    public $timestamps = false;

    protected $guarded = [

    ];


}
