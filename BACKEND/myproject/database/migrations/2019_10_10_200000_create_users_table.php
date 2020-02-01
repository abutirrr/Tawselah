<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {

            $table->bigIncrements('user_id');
            $table->string('user_name')->unique();
            $table->string('email');
            $table->string('password');
            $table->String('phone_number');
            $table->string('gender');
            $table->date('date_of_birth');
            $table->unsignedBigInteger('type_id')->default(1);

            $table->timestamps();

            $table->foreign('type_id')->references('type_id')->on('user_types');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}
