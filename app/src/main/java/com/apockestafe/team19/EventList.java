package com.apockestafe.team19;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;


public class EventList implements Observer{
    private final ArrayList<Event> list;

    @Override
    public void update (Observable observable, Object data) {
        for(Event event:list) {
            if(event.deleted) {
                delete(event);
            }
        }
    }

    public EventList(DataSnapshot dataSnapshot) {
        list = new ArrayList<>();
       /* userEmail = email.replaceAll("\\.", "@");
        try{
            dataSnapshot = dataSnapshot.child(userEmail);
        }
        catch (Exception e) {
            return;
        }*/
        for(DataSnapshot data : dataSnapshot.child("events").getChildren()) {
            addNewEvent(data);
        }
        FirebaseDatabase.getInstance().getReference("TEAM19/events").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addNewEvent(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                try{
                    list.remove(find(dataSnapshot.child("title").getValue(String.class)));
                }
                catch (Exception e) {return;}
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void addNewEvent(DataSnapshot data) {
        GenericTypeIndicator<ArrayList<RideInfo>> t = new GenericTypeIndicator<ArrayList<RideInfo>>() {};
        Event event = new Event(data.child("title").getValue(String.class),
                data.child("date").getValue(String.class),
                data.child("time").getValue(String.class),
                data.child("location").getValue(String.class),
                data.child("description").getValue(String.class),
                data.child("rideLocation").getValue(t));
        event.addObserver(this);
        add(event);
    }

    public boolean add(Event event) {
        if(find(event.getTitle()) == list.size()) {
            list.add(event);
            event.add();
            return true;
        }
        else{
            return false;
        }
    }

    public int find (String title) {
        int i;
        for(i = 0; i<list.size(); i++) {
            if(title.equals(list.get(i).getTitle()))
                break;
        }
        return i;
    }

    public void delete (Event event) {
        if(!(find(event.getTitle()) == list.size())) {
            list.remove(find(event.getTitle())).remove();
        }
    }

    public int size() {
        return list.size();
    }

    public Event get(int i) {
        return list.get(i);
    }

    public Event[] getArray() {
        Event[] array = new Event[list.size()];
        return list.toArray(array);
    }
}
