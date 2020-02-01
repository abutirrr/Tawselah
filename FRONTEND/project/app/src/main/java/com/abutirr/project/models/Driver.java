
package com.abutirr.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("car_id")
    @Expose
    private Integer carId;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("car")
    @Expose
    private Car car;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Driver() {
    }

    /**
     * 
     * @param createdAt
     * @param driverId
     * @param car
     * @param userId
     * @param user
     * @param carId
     * @param updatedAt
     */
    public Driver(Integer driverId, Integer userId, Integer carId, Object createdAt, Object updatedAt, Car car, User user) {
        super();
        this.driverId = driverId;
        this.userId = userId;
        this.carId = carId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.car = car;
        this.user = user;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
