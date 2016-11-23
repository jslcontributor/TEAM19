package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class EventActivity extends AppCompatActivity {

    private EditText title, date, time, location, description;
    private Button createButton, cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
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
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event(title.getText().toString(),
                        date.getText().toString(), time.getText().toString(),
                        location.getText().toString(), description.getText().toString(),
                        null);
                event.add();
                event.setCount();
                finish();
                //startActivity(new Intent(EventActivity.this, LoginActivity.class));
            }
        });
    }
}
