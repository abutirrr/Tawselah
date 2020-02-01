<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRidesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {   // driver rides
        Schema::create('rides', function (Blueprint $table) {
            $table->bigIncrements('ride_id');
            $table->unsignedBigInteger('driver_id');
            $table->string('source_city');
            $table->string('destination_city');
            $table->string('source_pin');
            $table->string('destination_pin');
            $table->time('ride_time');
            $table->timestamp('ride_date');
            $table->float('price');
            $table->integer('seats_booked')->default(0);   // number of seats which have been booked
            $table->integer('seats_available'); // number of seats set on rides creation

            $table->timestamps();

            $table->foreign('driver_id')->references('driver_id')->on('drivers')
                ->onDelete('cascade');

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('rides');
    }
}
