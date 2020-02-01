package com.abutirr.project.Api;

import com.abutirr.project.models.BecomeDriverResponse;
import com.abutirr.project.models.CreateAUser;
import com.abutirr.project.models.ReturnDriverIdResponse;
import com.abutirr.project.models.Ride;
import com.abutirr.project.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {


    @FormUrlEncoded
    @POST("users/createAUser")
    Call<CreateAUser> createAUser(
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone_number") String phone_number,
            @Field("gender") String gender,
            @Field("date_of_birth") String date_of_birth
    );

    @FormUrlEncoded
    @POST("userLogin")
    Call<User> userLogin(
            @Field("user_name") String user_name,
            @Field("password") String password
    );

    @FormUrlEncoded
    @PATCH("users/{id}")
    Call<User> updateAUser(@Path("id") int Id,
                              @Field("password") String password,
                              @Field("email") String email,
                              @Field("phone_number") String phone_number,
                              @Field("date_of_birth") String date_of_birth
    );

    @FormUrlEncoded
    @POST("users/becomeADriver")
    Call<BecomeDriverResponse> becomeADriver(
            @Field("user_id") int user_id,
            @Field("car_model") String carModel ,
            @Field("car_license") String carLicense

    );
    @FormUrlEncoded
    @POST("users/returnDriverId")
    Call<ReturnDriverIdResponse> returnDriverId(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("users/searchForARide")
    Call<List<Ride>> searchForRides(
            @Field("source_city") String source_city,
            @Field("destination_city") String destination_city
    );

    @FormUrlEncoded
    @POST("users/joinARide")
    Call<ResponseBody> joinARide(
            @Field("user_id") int user_id,
            @Field("ride_id") int ride_id
    );

    @FormUrlEncoded
    @POST("users/fetchJoinedTrips")
    Call<List<Ride>> fetchJoinedTrips(
            @Field("user_id") int user_id
    );
    @FormUrlEncoded
    @POST("drivers/fetchCreatedRides")
    Call<List<Ride>> fetchDriverCreatedRides(
            @Field("driver_id") int driver_id
    );

    // i did it using POST , it should be done using DELETE
    @FormUrlEncoded
    @POST("users/removeJoinedTrip")
    Call<ResponseBody> removeJoinedTrip(
            @Field("user_id") int user_id,
            @Field("ride_id") int ride_id

    );

    @DELETE("rides/{ride_id}")
    Call<ResponseBody> removeCreatedRide(
            @Path("ride_id") int ride_id
    );

    @FormUrlEncoded
    @POST("drivers/createARide")
    Call<ResponseBody> createARide(
            @Field("driver_id") int driver_id,
            @Field("source_city") String source_city,
            @Field("destination_city") String destination_city,
            @Field("source_pin") String source_pin,
            @Field("destination_pin") String destination_pin,
            @Field("ride_time") String ride_time,
            @Field("ride_date") String ride_date,
            @Field("price") String price,
            @Field("seats_available") int seats_available
    );
}
