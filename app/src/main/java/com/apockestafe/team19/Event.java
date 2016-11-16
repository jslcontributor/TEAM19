package com.apockestafe.team19;

import java.util.ArrayList;

/**
 * Created by JLee on 10/22/16.
 */
public class Event {

    private String title, date, time, location, description;
    private ArrayList<ArrayList<String>> rideLocation;

    public Event(String title, String date, String time, String location, String description, ArrayList<ArrayList<String>> rideLocation ) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.rideLocation = rideLocation;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<ArrayList<String>> getRideLocation() { return rideLocation; }

    public void addRideLocation(ArrayList<String> rd) {

        rideLocation.add(rd);
    }
}

