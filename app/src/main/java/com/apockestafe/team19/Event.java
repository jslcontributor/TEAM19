package com.apockestafe.team19;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Observable;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Observable;


public class Event extends Observable {

    private String title, date, time, location, description;
    //private ArrayList<ArrayList<String>> rideLocation;
    private String[] rideLocation;
    private final DatabaseReference ref;
    public boolean deleted;
    public long counter;

    public Event(String title, String date, String time, String location,
                 String description, String[] rideLocation) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //String key = ServerValue.TIMESTAMP.toString();
        // String key;
        setCounter();
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.rideLocation = rideLocation;


        ref = database.getReference("TEAM19/events/" + String.valueOf(counter));
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                changeData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                deleted = true;
                notifyObservers();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void changeData (DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        if(key.equals("title")) {
            title = dataSnapshot.getValue(String.class);
        }
        if(key.equals("time")) {
            time = dataSnapshot.getValue(String.class);
        }
        if(key.equals("date")) {
            date = dataSnapshot.getValue(String.class);
        }
        if(key.equals("location")) {
            location = dataSnapshot.getValue(String.class);
        }
        if(key.equals("description")) {
            description = dataSnapshot.getValue(String.class);
        }
        if(key.equals("rideLocation")) {
            //rideLocation = dataSnapshot.getValue(String[].class);
        }
    }

    public void add() {
        ref.setValue(this);

    }

    public void remove() {
        ref.setValue(null);
        deleted = true;
        notifyObservers();
    }

    public void changeTitle (String title) {
        if(deleted) {
            return;
        }
        this.title = title;
        ref.child("title").setValue(title);
    }

    public void changeTime (String time) {
        if(deleted) {
            return;
        }
        this.time = time;
        ref.child("time").setValue(time);
    }

    public void changeDate (String date) {
        if(deleted) {
            return;
        }
        this.date = date;
        ref.child("date").setValue(date);
    }

    public void changeLocation (String location) {
        if(deleted) {
            return;
        }
        this.location = location;
        ref.child("location").setValue(location);
    }

    public void changeDescription (String description) {
        if(deleted) {
            return;
        }
        this.description = description;
        ref.child("description").setValue(description);
    }

    public void changeRideLocation (String[] rideLocation) {
        if(deleted) {
            return;
        }
        this.rideLocation = rideLocation;
        ref.child("rideLocation").setValue(rideLocation);
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

    public String[] getRideLocation() { return rideLocation; }

    public void addRideLocation(String[] rd) {

        //rideLocation.add(rd);
    }

    public boolean equals (Object comparable) {return false;} //implement if need comparator

    public String toString() { //implement for event display in listview?
        return null;
    }

    public void setCounter() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref2 = database.getReference("TEAM19");
        ref2.child("counter").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    counter = 0;
                }
                else{
                    counter = (long)dataSnapshot.getValue();
                    counter++;
                    ref2.child("counter").setValue(counter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

