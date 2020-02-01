
package com.abutirr.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot {

    @SerializedName("user_user_id")
    @Expose
    private Integer userUserId;
    @SerializedName("rides_ride_id")
    @Expose
    private Integer ridesRideId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Pivot() {
    }

    /**
     * 
     * @param ridesRideId
     * @param userUserId
     */
    public Pivot(Integer userUserId, Integer ridesRideId) {
        super();
        this.userUserId = userUserId;
        this.ridesRideId = ridesRideId;
    }

    public Integer getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(Integer userUserId) {
        this.userUserId = userUserId;
    }

    public Integer getRidesRideId() {
        return ridesRideId;
    }

    public void setRidesRideId(Integer ridesRideId) {
        this.ridesRideId = ridesRideId;
    }

}
