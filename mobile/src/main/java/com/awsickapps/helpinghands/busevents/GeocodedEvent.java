package com.awsickapps.helpinghands.busevents;

/**
 * Created by kritarie on 2/28/15.
 */
public class GeocodedEvent {

    private String address;
    private String city;
    private String state;

    public GeocodedEvent() {
    }

    public GeocodedEvent(String address, String state, String city) {
        this.address = address;
        this.state = state;
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
