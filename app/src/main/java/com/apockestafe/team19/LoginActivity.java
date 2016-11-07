package com.apockestafe.team19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import com.firebase.client.Firebase;


public class LoginActivity extends AppCompatActivity {

    private ListView scrolllList;
    private Button addbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Firebase.setAndroidContext(this);
        String[] eventString = new String[] {"Leslie's Super Sweet 16 09/20"};
        final List<String> listStringer = new ArrayList<String>(Arrays.asList(eventString));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventString);

        setContentView(R.layout.activity_main);
        scrolllList = (ListView) findViewById(R.id.listView);
        scrolllList.setAdapter(arrayAdapter);
        addbutton = (Button) findViewById(R.id.button1);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, EventActivity.class));





            }
        });
    }
}
