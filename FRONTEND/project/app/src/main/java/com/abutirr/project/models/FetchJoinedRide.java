
package com.abutirr.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchJoinedRide {

    @SerializedName("ride_id")
    @Expose
    private Integer rideId;
    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("source_city")
    @Expose
    private String sourceCity;
    @SerializedName("destination_city")
    @Expose
    private String destinationCity;
    @SerializedName("source_pin")
    @Expose
    private String sourcePin;
    @SerializedName("destination_pin")
    @Expose
    private String destinationPin;
    @SerializedName("ride_time")
    @Expose
    private String rideTime;
    @SerializedName("ride_date")
    @Expose
    private String rideDate;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("seats_booked")
    @Expose
    private Integer seatsBooked;
    @SerializedName("seats_available")
    @Expose
    private Integer seatsAvailable;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("pivot")
    @Expose
    private Pivot pivot;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FetchJoinedRide() {
    }

    /**
     * 
     * @param rideTime
     * @param rideId
     * @param destinationPin
     * @param rideDate
     * @param seatsAvailable
     * @param destinationCity
     * @param createdAt
     * @param driverId
     * @param price
     * @param pivot
     * @param sourceCity
     * @param sourcePin
     * @param seatsBooked
     * @param updatedAt
     */
    public FetchJoinedRide(Integer rideId, Integer driverId, String sourceCity, String destinationCity, String sourcePin, String destinationPin, String rideTime, String rideDate, Double price, Integer seatsBooked, Integer seatsAvailable, Object createdAt, Object updatedAt, Pivot pivot) {
        super();
        this.rideId = rideId;
        this.driverId = driverId;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.sourcePin = sourcePin;
        this.destinationPin = destinationPin;
        this.rideTime = rideTime;
        this.rideDate = rideDate;
        this.price = price;
        this.seatsBooked = seatsBooked;
        this.seatsAvailable = seatsAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.pivot = pivot;
    }

    public Integer getRideId() {
        return rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getSourcePin() {
        return sourcePin;
    }

    public void setSourcePin(String sourcePin) {
        this.sourcePin = sourcePin;
    }

    public String getDestinationPin() {
        return destinationPin;
    }

    public void setDestinationPin(String destinationPin) {
        this.destinationPin = destinationPin;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(Integer seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
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

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

}
