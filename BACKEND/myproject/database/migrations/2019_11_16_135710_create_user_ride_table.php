<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUserRideTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('user_ride', function (Blueprint $table) {
            $table->unsignedBigInteger('user_user_id');
            $table->unsignedBigInteger('rides_ride_id');


            $table->timestamps();

            $table->primary(['user_user_id','rides_ride_id']);

            $table->foreign('user_user_id')->references('user_id')
                ->on('users')->onDelete('cascade');
            $table->foreign('rides_ride_id')->references('ride_id')
                ->on('rides')->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('user_ride');
    }
}
