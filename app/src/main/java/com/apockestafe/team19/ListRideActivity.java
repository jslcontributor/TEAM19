package com.apockestafe.team19;

import com.apockestafe.team19.Event;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListRideActivity extends AppCompatActivity {

    private Button submitRide, cancelRide;
    //private TextView address, seatCount;
    private EditText streetAddressValue, cityValue, stateValue, zipcodeValue, seatCountValue;
    private TextView errorText;
    private boolean rideListed;
    private int seatCount;
    private String addressValue;
    private ArrayList<String> carData = new ArrayList<>();
    private Event e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ride);

        streetAddressValue = (EditText) findViewById(R.id.streetAddressValue);
        cityValue = (EditText) findViewById(R.id.cityValue);
        stateValue = (EditText) findViewById(R.id.stateValue);
        zipcodeValue = (EditText) findViewById(R.id.zipcodeValue);
        seatCountValue = (EditText) findViewById(R.id.seatCountValue);
        errorText = (TextView)findViewById(R.id.errorText);

        submitRide = (Button) findViewById(R.id.submitRide);
        submitRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            addressValue = createAddress(streetAddressValue, cityValue, stateValue, zipcodeValue);
            rideListed = checkIfListedRide(addressValue);
            seatCount = getSeatCount(seatCountValue);
            System.out.println("Valid address: " + checkValidAddress(addressValue));

            final String val = getIntent().getStringExtra("eventNumber");

            if (addressValue.length() == 0 && seatCount == 0) {
                errorText.setText("Error. Enter your car address and seat count");
            } else if (addressValue.length() == 0) {
                errorText.setText("Error. Enter your car address");
            } else if (!checkValidAddress(addressValue)) {
                errorText.setText("Not a valid address.");
            } else if (seatCount == 0) {
                errorText.setText("Error. Enter your seat count.");
            }  else if (!rideListed && addressValue.length() > 0 && seatCount != 0 && checkValidAddress(addressValue)) {
                errorText.setText(" ");
                final DatabaseReference ref;
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                ref = database.getReference("TEAM19/events/" + val);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //                        String key = "aee";
                        final DatabaseReference ref;
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        ref = database.getReference("TEAM19/events/" + val);
                        ArrayList<String> peopleInCar = new ArrayList<>();
                        Context c = getApplicationContext();
                        LatLng ll = getLocationFromAddress(c, addressValue);
                        RideInfo ri = new RideInfo(addressValue, peopleInCar, seatCount); //, ll);
                        List<RideInfo> rideInfo;
                        GenericTypeIndicator<List<RideInfo>> t = new GenericTypeIndicator<List<RideInfo>>() {};
                        rideInfo = dataSnapshot.child("rideLocation").getValue(t);
                        if (rideInfo == null)
                            rideInfo = new ArrayList<>();
                        rideInfo.add(ri);
                        ref.child("rideLocation").setValue(rideInfo);



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
                Intent i = new Intent(ListRideActivity.this, EventInfo.class);
                i.putExtra("eventNumber", val);
                System.out.println("EVENT NUMBER: " + val);
                startActivity(i);
            } else {
                errorText.setText("You've already listed your ride.");
            }


            }
        });

        cancelRide = (Button) findViewById(R.id.cancelRide);
        cancelRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String val = getIntent().getStringExtra("eventNumber");
                Intent i = new Intent(ListRideActivity.this, EventInfo.class);
                i.putExtra("eventNumber", val);
                startActivity(i);
                finish();
            }
        });
    }

    public boolean checkIfListedRide(String aV) {
//        ArrayList<String> cL = e.getRideLocation();
//        for (int i = 0; i < cL.size(); i++) {
//            if (carLocation.compareTo(cL.get(i)) == 0) {
//                return true;
//            }
//        }
        return false;
    }

    public int getSeatCount(EditText scv) {
        String sc = scv.getText().toString();
        int seatCountInt = 0;
        if (sc.matches("[0-9]+"))
            seatCountInt = Integer.parseInt(sc);
        return seatCountInt;
    }

    public String createAddress(EditText sav, EditText cv, EditText sv, EditText zcv) {
        String address = sav.getText().toString() + ", " +
                        cv.getText().toString() + ", " +
                        sv.getText().toString() + ", " +
                        zcv.getText().toString();

        if (address.compareTo(", , , ") == 0) {
            System.out.println("Address: ");
            return "";
        }

        System.out.println("Address: " + address);
        return address;
    }

    public boolean checkValidAddress(String streetAddress) {
        boolean validAddress = false;
        LatLng latlng;
        latlng = getLocationFromAddress(this, streetAddress);
        if (latlng != null)
            validAddress = true;

        return validAddress;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng latlng = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            latlng = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return latlng;
    }


}