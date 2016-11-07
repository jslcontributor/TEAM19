package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class EventInfo extends AppCompatActivity {

    private Button mapButton, listRideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        mapButton = (Button)findViewById(R.id.MapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventInfo.this, MapsActivity.class));

            }
        });

        listRideButton = (Button)findViewById(R.id.listRideButton);
        listRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventInfo.this, ListRideActivity.class));

            }
        });
    }
}
