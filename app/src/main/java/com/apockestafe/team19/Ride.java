package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Ride extends AppCompatActivity {

    private Button backButton, addToCarButton, removeFromCarButton;
    private TextView seatsValue, errorText;
//    Event e;
//    ArrayList<ArrayList<String>> rideData = e.getRideLocation();
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
                    // if not signed up for ride, do
                    if (rideCount == 0 ) {
                        errorText.setText("No more available spots");
                    } else {
                        rideCount--;
                        seatsValue.setText(rideCount + "");

                    }
                }
            });

        removeFromCarButton = (Button) findViewById(R.id.removeFromCarButton);
        removeFromCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if already signed up for ride, do
                rideCount++;
                seatsValue.setText(rideCount + "");
                errorText.setText("");
            }
        });


        rideCount = 3;
        seatsValue = (TextView) findViewById(R.id.seatsValue);
        seatsValue.setText(rideCount + "");

//        rideCount = getRideCount(rideData);


    }

//    private ArrayList<String[]> getRideData() {
//        ArrayList<String[]> data = e.getRideLocation();
//            return data;
//    }

    private int getRideCount(ArrayList<ArrayList<String>> rd) {
        int size = Integer.parseInt(rd.get(0).get(1));
        return size;
    }
}
