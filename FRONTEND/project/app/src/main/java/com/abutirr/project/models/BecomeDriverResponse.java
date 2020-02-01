
package com.abutirr.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BecomeDriverResponse {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("car_model")
    @Expose
    private String carModel;
    @SerializedName("car_license")
    @Expose
    private String carLicense;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BecomeDriverResponse() {
    }

    /**
     * 
     * @param userId
     * @param carLicense
     * @param carModel
     */
    public BecomeDriverResponse(Integer userId, String carModel, String carLicense) {
        super();
        this.userId = userId;
        this.carModel = carModel;
        this.carLicense = carLicense;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

}
