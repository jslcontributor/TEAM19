package com.apockestafe.team19;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
//import com.firebase.client.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private ListView scrollList;
    private Button addbutton, settingsButton;
    private FirebaseAuth auth;
    private SharedPreferencesEditor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        editor = new SharedPreferencesEditor(getSharedPreferences("signIn", MODE_PRIVATE));
        super.onCreate(savedInstanceState);
       // Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null) {
            //todo: handle this shit if facebook login but no fuegobase account
            //also, handle the setiingsactivity so when you connect facebook button, shithappens
            //also, handle setiingsactivity signoutshit with facebook
            //also,
        }
        setContentView(R.layout.activity_main);

        scrollList = (ListView) findViewById(R.id.listView);
        scrollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, EventInfo.class);
                startActivity(newActivity);
            }
        });

        addbutton = (Button) findViewById(R.id.button1);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
            }
        });

        settingsButton = (Button) findViewById(R.id.button2);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SetiingsActivity.class));
            }
        });
    }


}
