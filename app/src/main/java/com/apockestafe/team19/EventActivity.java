package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.os.SystemClock.sleep;

public class EventActivity extends AppCompatActivity {

    private EditText title, date, time, location, description;
    private Button createButton, cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setTitle("Create Event");
        final SharedPreferencesEditor sp = new SharedPreferencesEditor(getSharedPreferences("login", MODE_PRIVATE));
        title = (EditText)findViewById(R.id.title);
        date = (EditText)findViewById(R.id.date);
        time = (EditText)findViewById(R.id.time);
        location = (EditText)findViewById(R.id.location);
        description = (EditText)findViewById(R.id.description);
        createButton = (Button)findViewById(R.id.button_create);
        cancelButton = (Button)findViewById(R.id.button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventActivity.this, MainActivity.class));
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<RideInfo> ri = new ArrayList<>(0);
                ArrayList<String> itemsList = new ArrayList<>(0);
                Event event = new Event(title.getText().toString(),
                        date.getText().toString(), time.getText().toString(),
                        location.getText().toString(), description.getText().toString(),
                         ri, itemsList);
                event.add();
                sleep(1000);
                startActivity(new Intent(EventActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
