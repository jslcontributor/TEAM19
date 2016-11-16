package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ListRideActivity extends AppCompatActivity {

    private Button submitRide, cancelRide;
    private EditText addressValue, seatCountValue;
    private boolean rideListed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ride);

//        addressValue = (EditText)findViewById(R.id.addressValue);
//        seatCountValue = (EditText)findViewById(R.id.seatCountValue);

        submitRide = (Button)findViewById(R.id.submitRide);
        submitRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rideListed) {
                    startActivity(new Intent(ListRideActivity.this, EventInfo.class));

                } else {

                }

            }
        });

        cancelRide = (Button)findViewById(R.id.cancelRide);
        cancelRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
