package com.apockestafe.team19;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nsallaire on 11/15/16.
 */

public class RideInfo {

    private String carAddress;
    private List<String> peopleInCar;
    private int numberSeatsInCar;
    private LatLng latlng;

    public RideInfo(String carAddress, List<String> peopleInCar, int numberSeatsInCar) { //}, LatLng latlng) {
        this.carAddress = carAddress;
        this.peopleInCar = peopleInCar;
        this.numberSeatsInCar = numberSeatsInCar;
        //this.latlng = latlng;
    }

    public RideInfo() {

    }

    public void setCarAddress(String cA) {
        this.carAddress = cA;
    }

    public void setPeopleInCar(List<String> pic) {
        this.peopleInCar = pic;
    }

//    public void setLatlng(LatLng ll) {
//        this.latlng = ll;
//    }

    public void setNumberSeatsInCar(int nsic) {
        this.numberSeatsInCar = nsic;
    }

    public String getCarAddress() {
        return this.carAddress;
    }

    public List<String> getPeopleInCar() {
        return this.peopleInCar;
    }

    public int getNumberSeatsInCar() {
        return this.numberSeatsInCar;
    }

//    public LatLng getLatLng() { return this.latlng; }


}
