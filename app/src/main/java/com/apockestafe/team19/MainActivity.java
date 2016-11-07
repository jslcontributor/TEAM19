package com.apockestafe.team19;

import android.app.ListActivity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
//import com.firebase.client.Firebase;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import static com.facebook.internal.FacebookDialogFragment.TAG;


public class MainActivity extends AppCompatActivity {

    private ListView scrollList;
    private Button addbutton, settingsButton;
    private FirebaseAuth auth;
    private SharedPreferencesEditor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();
        //String token = editor.getToken();
        try {
            System.out.println(AccessToken.getCurrentAccessToken().getToken());
        }
        catch(NullPointerException e) {
            System.out.println("aeljaejla");
        }
        String token = AccessToken.getCurrentAccessToken().getToken();
        handleFacebookAccessToken(token);

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


    private void handleFacebookAccessToken(String token) {
        Log.d("herehere", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token);
        auth.signInWithCredential(credential).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                if(!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential", task.getException());
                }
            }
        });
    }
}
