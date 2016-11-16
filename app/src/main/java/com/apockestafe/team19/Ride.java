package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    private TextView seatsValue, errorText;
    private SharedPreferencesEditor editor;



    private int rideCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        errorText = (TextView) findViewById(R.id.errorText);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addToCarButton = (Button) findViewById(R.id.addToCarButton);
        addToCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            editor = new SharedPreferencesEditor(getSharedPreferences("marker", MODE_PRIVATE));
            final DatabaseReference ref;
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            String key = "aee";
            ref = database.getReference("events/" + key);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<List<RideInfo>> t = new GenericTypeIndicator<List<RideInfo>>() {
                    };
                    List<RideInfo> rideInfo = dataSnapshot.child("rideLocation").getValue(t);

                    String markerAddress = editor.getMarker();
                    System.out.println("MARKER ADDRESS: " + markerAddress);

                    for (int i = 0; i < rideInfo.size(); i++) {
                        System.out.println("Ride Address: " + rideInfo.get(i).getCarAddress());

                        if (rideInfo.get(i).getCarAddress().equals(markerAddress)) {
                            RideInfo ri = rideInfo.get(i);
                            if (ri.getNumberSeatsInCar() == 0) {
                                errorText.setText("No more available spots");
                            } else {
                                if (ri.getPeopleInCar() == null) {
                                    List<String> people = new ArrayList<>();
                                    people.add(editor.getMyEmail());
                                    ri.setPeopleInCar(people);
                                } else {
                                    boolean inCar = false;
                                    for (int j = 0; j < ri.getPeopleInCar().size(); j++) {
                                        if (ri.getPeopleInCar().get(j).equals(editor.getMyEmail())) {
                                            inCar = true;
                                        }
                                    }
                                    if (!inCar) {
                                        ri.getPeopleInCar().add(editor.getMyEmail());
                                        rideCount = ri.getNumberSeatsInCar();
                                        rideCount--;
                                        ri.setNumberSeatsInCar(rideCount);
                                        seatsValue.setText((rideCount));
                                        errorText.setText("Added to this car.");
                                    }
                                }
                                ref.child("rideLocation").setValue(rideInfo);
                                seatsValue.setText(ri.getNumberSeatsInCar() + "");

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
                String key = "aee";
                ref = database.getReference("events/" + key);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                rideCount++;
                seatsValue.setText(rideCount + "");
                errorText.setText("");
            }
        });
    }
}
