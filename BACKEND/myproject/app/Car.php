<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Car extends Model
{
    protected $primaryKey='car_id';
    protected $table='cars';

    public function drivers(){
        return $this->hasMany(Driver::class,'car_id');
    }

    public $timestamps=false;
    protected $guarded=[

    ];
}
