package com.apockestafe.team19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private ListView scrolllList;
    private Button addbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrolllList = (ListView) findViewById(R.id.listView);
        addbutton = (Button) findViewById(R.id.button1);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));





            }
        });
    }
}
