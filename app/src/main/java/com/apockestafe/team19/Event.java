package com.apockestafe.team19;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.List;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

import static android.os.SystemClock.sleep;


public class Event extends Observable {

    private String title, date, time, location, description;
    private List<RideInfo> rideLocation;
    //private ArrayList<ArrayList<String>> rideLocation;
    //private String[] rideLocation;
    private  DatabaseReference ref;
    private  FirebaseDatabase database;
    public boolean deleted;
    public int counter;
    //private final AtomicInteger count;

    public Event(String title, String date, String time, String location,
                 String description, List<RideInfo> rideLocation) {

        //count = new AtomicInteger();

        //String key = ServerValue.TIMESTAMP.toString();
        // String key;
     //   System.out.println("justin"+count.get());
      //  setCounter();
       /* Thread back = new Thread() {
            public void run() {
                setCounter();
            }
        };
        back.start();*/
       // setCounter();

        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.rideLocation = rideLocation;


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
            GenericTypeIndicator<List<RideInfo>> t = new GenericTypeIndicator<List<RideInfo>>() {};
            rideLocation = dataSnapshot.getValue(t);
        }
    }

    public void add() {
        //  System.out.println("1234" + count.get());
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("TEAM19");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean added = false;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.child("counter").getValue();
                count++;
                String s = Long.toString(count);
                Log.d("CounterVariable", s);
                System.out.println("Count: " + s);
                addHelper(s, count);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addHelper(String s, long count) {
        ref.child("events").child(""+s).setValue(this);
    }

    public void setCount() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("TEAM19");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = (long) dataSnapshot.child("counter").child("counter").getValue();
                count++;
                ref.child("counter").child("counter").setValue(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public void changeRideLocation (ArrayList<RideInfo> rideLocation) {
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

    public List<RideInfo> getRideLocation() { return rideLocation; }

    public void addRideLocation(String[] rd) {

    }
  //  public boolean equals(Object comparable) {return false;} //implement if need comparator

    public String toString() { //implement for event display in listview?
        return null;
    }

//    public void setCounter() {
//        System.out.println("999"+count.get());
//        ref.child("events").child(String.valueOf(count.get())).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                changeData(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                deleted = true;
//                notifyObservers();
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

}

