<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class user_type extends Model
{
    protected $primaryKey='type_id';
    protected $table='user_types';

    public $timestamps=false;
    protected $guarded=[

    ];

}
