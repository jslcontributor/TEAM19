package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Ride extends AppCompatActivity {

    private Button backButton, addToCarButton, removeFromCarButton;
    private TextView seatsValue, errorText, listViewText;
    private SharedPreferencesEditor editor;
    private int rideCount;
    private ListView scrollList;
    private final ArrayList<String> aList = new ArrayList<>();
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        setTitle("Ride Info");

        final String val = getIntent().getStringExtra("Marker Name");
        String[] split = val.split(":");
        final String s = split[0];
        final String key = split[1];

        seatsValue = (TextView) findViewById(R.id.seatsValue);
        errorText = (TextView) findViewById(R.id.errorText);
        listViewText = (TextView) findViewById(R.id.words);

        scrollList = (ListView) findViewById(R.id.listViewOfPeople);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Ride.this, MapsActivity.class);
                i.putExtra("eventNumber", key);
                startActivity(i);
            }
        });

        editor = new SharedPreferencesEditor(getSharedPreferences("login", MODE_PRIVATE));
        final DatabaseReference ref;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

//        System.out.println("MARKER NAME: " + s);
//        System.out.println("KEY FOR RIDE.JAVA: " + key);
//        String key = "aee";
        errorText.setText(s);

        ref = database.getReference("TEAM19/events/" + key);

        addToCarButton = (Button) findViewById(R.id.addToCarButton);
        addToCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ref.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<List<RideInfo>> t = new GenericTypeIndicator<List<RideInfo>>() {};
                        List<RideInfo> rideInfo = dataSnapshot.child("rideLocation").getValue(t);

//                        String markerAddress = editor.getMarker();
    //                    System.out.println("MARKER ADDRESS: " + markerAddress);
                        // Need to get street address or convert to LatLng
                        for (int i = 0; i < rideInfo.size(); i++) {


                            if (rideInfo.get(i).getCarAddress().equals(s)) {
                                RideInfo ri = rideInfo.get(i);
                                if (ri.getNumberSeatsInCar() == 0) {
                                    errorText.setText("No more available spots");
                                } else {
                                    if (ri.getPeopleInCar() == null) {
                                        List<String> people = new ArrayList<>();
                                        ri.setPeopleInCar(people);
                                    }
                                    boolean inCar = false;
                                    boolean inAnotherCar = checkIfPersonIsInAnyCar(rideInfo, editor.getMyEmail(), ri.getCarAddress());

                                    System.out.println("Email: " + editor.getMyEmail());
                                    for (int j = 0; j < ri.getPeopleInCar().size(); j++) {
                                        if (ri.getPeopleInCar().get(j).equals(editor.getMyEmail())) {
                                            inCar = true;
                                        }
                                    }
//                                    System.out.println("Value of inCar: " + inCar);
                                    if (!inCar && !inAnotherCar) {
//                                        System.out.println("User added to car: " + editor.getMyEmail());
                                        ri.getPeopleInCar().add(editor.getMyEmail());
                                        addPersonToList(editor.getMyEmail());
                                        rideCount = ri.getNumberSeatsInCar();
//                                        System.out.println("SEAT COUNT: " + rideCount);
                                        rideCount--;
                                        ri.setNumberSeatsInCar(rideCount);
                                        seatsValue.setText(ri.getNumberSeatsInCar() + "");
                                        errorText.setText("Added to this car.");
                                    } else if (inCar && !inAnotherCar) {
                                        errorText.setText("Already added to this car");
                                    } else if (!inCar && inAnotherCar) {
                                        errorText.setText("Already signed up for another car");
                                    }

                                    ref.child("rideLocation").setValue(rideInfo);


                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        });

        removeFromCarButton = (Button) findViewById(R.id.removeFromCarButton);
        removeFromCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = new SharedPreferencesEditor(getSharedPreferences("login",MODE_PRIVATE));
                // if already signed up for ride, do
                final DatabaseReference ref;
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
//                String key = "aee";
                ref = database.getReference("TEAM19/events/" + key);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<List<RideInfo>> t = new GenericTypeIndicator<List<RideInfo>>() {};
                        List<RideInfo> rideInfo = dataSnapshot.child("rideLocation").getValue(t);
                        for (int i = 0; i < rideInfo.size(); i++) {
                            if (rideInfo.get(i).getCarAddress().equals(s)) {
                                RideInfo ri = rideInfo.get(i);
                                boolean inCar = false;
                                if (ri.getPeopleInCar() != null) {
                                    for (int j = 0; j < ri.getPeopleInCar().size(); j++) {
                                        if (ri.getPeopleInCar().get(j).equals(editor.getMyEmail())) {
                                            inCar = true;
                                        }
                                    }
                                }
                                if (inCar) {
                                    for (int k = 0; k < ri.getPeopleInCar().size(); k++) {
                                        if (ri.getPeopleInCar().get(k).equals(editor.getMyEmail())) {
                                            removePersonFromList(ri.getPeopleInCar().get(k));
                                            ri.getPeopleInCar().remove(k);
                                            rideCount = ri.getNumberSeatsInCar();
                                            rideCount++;
                                            ri.setNumberSeatsInCar(rideCount);
                                            seatsValue.setText(ri.getNumberSeatsInCar() + "");
                                            errorText.setText("You have been removed from this ride");
                                        }
                                    }
                                } else {
                                    errorText.setText("Cannot remove. You are not in this ride");
                                }
                            }
                        }
                        ref.child("rideLocation").setValue(rideInfo);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<RideInfo>> t = new GenericTypeIndicator<List<RideInfo>>() {};
                List<RideInfo> rideInfo = dataSnapshot.child("rideLocation").getValue(t);
                if (rideInfo != null) {
                    for (int i = 0; i < rideInfo.size(); i++) {
                        if (rideInfo.get(i).getCarAddress().equals(s)) {
                            rideCount = rideInfo.get(i).getNumberSeatsInCar();
                            if (rideInfo.get(i).getPeopleInCar() != null)
                                createListOfPeople(rideInfo.get(i).getPeopleInCar());
                            else {
                                listViewText.setText("No one has signed up for this ride");
                            }

                        }
                    }
                }
                seatsValue.setText(rideCount + "");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createListOfPeople(List<String> people) {
        if (people != null || people.size() != 0) {
            for (int i = 0; i < people.size(); i++)
                aList.add(people.get(i));
            adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, aList);
            scrollList.setAdapter(adapter);
        }
    }

    private void removePersonFromList(String person) {
        for (int i = 0; i < aList.size(); i++)
            if (aList.get(i).equals(person))
                aList.remove(i);
        if (aList.size() == 0)
            listViewText.setText("No one has signed up for this ride");
        else
            listViewText.setText("Friends Currently Signed Up");
        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, aList);
        scrollList.setAdapter(adapter);
    }

    private void addPersonToList(String person) {
        aList.add(person);
        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, aList);
        scrollList.setAdapter(adapter);
        listViewText.setText("Friends Currently Signed Up");
    }

    private boolean checkIfPersonIsInAnyCar(List<RideInfo> rideInfo, String person, String address ) {
        for (int i = 0; i < rideInfo.size(); i++) {
            RideInfo ri = rideInfo.get(i);
            List<String> people = ri.getPeopleInCar();
            if (people != null)
                for (int j = 0; j < people.size(); j++) {
                    if (people.get(j).equals(person) && !(address.equals(ri.getCarAddress())))
                        return true;
            }
        }

        return false;
    }
}
