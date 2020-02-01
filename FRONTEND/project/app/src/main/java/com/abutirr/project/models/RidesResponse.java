package com.abutirr.project.models;

import java.util.List;

public class RidesResponse {

    private List<Ride> rides ;

    public RidesResponse(List<Ride> rides) {
        this.rides = rides;
    }

    public List<Ride> getRides() {
        return rides;
    }
}

