
package com.abutirr.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {

    @SerializedName("car_id")
    @Expose
    private Integer carId;
    @SerializedName("car_model")
    @Expose
    private String carModel;
    @SerializedName("car_license")
    @Expose
    private String carLicense;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Car() {
    }

    /**
     * 
     * @param createdAt
     * @param carLicense
     * @param carId
     * @param carModel
     * @param updatedAt
     */
    public Car(Integer carId, String carModel, String carLicense, Object createdAt, Object updatedAt) {
        super();
        this.carId = carId;
        this.carModel = carModel;
        this.carLicense = carLicense;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
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

}
