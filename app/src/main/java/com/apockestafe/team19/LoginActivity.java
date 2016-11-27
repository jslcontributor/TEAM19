package com.apockestafe.team19;

import com.apockestafe.team19.Event;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import com.firebase.client.Firebase;


public class LoginActivity extends AppCompatActivity {

    private ListView scrollList;
    private Button addbutton;
    private Event e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Firebase.setAndroidContext(this);
        ArrayList<String> rides = new ArrayList<>();
        rides.add("2915 Estancia, San Clemente, CA, 92670");
        rides.add("21 Edendale St, Ladera Ranch, CA, 92694");
        String[] eventString = new String[] {"Leslie's Super Sweet 16 09/20"};
//        e = new Event("Leslie's Super Sweet 16", "11/15/16", "9:00pm", "3853 Van Dyke Ave, San Diego, CA, 92105", "Let's Party", rides);
        final List<String> listStringer = new ArrayList<String>(Arrays.asList(eventString));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventString);

        setContentView(R.layout.activity_main);
        scrollList = (ListView) findViewById(R.id.listView);
        scrollList.setAdapter(arrayAdapter);
        scrollList = (ListView) findViewById(R.id.listView);
        scrollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(LoginActivity.this, EventInfo.class);
                startActivity(newActivity);
            }
        });


        addbutton = (Button) findViewById(R.id.button1);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, EventActivity.class));





            }
        });
    }
}


/*
package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.WebDialog;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.GameRequestDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EventInfo extends AppCompatActivity {

   // private Button mapButton, listRideButton, backButton;
    private Button mapButton, listRideButton, backButton, inviteButton;
    private DatabaseReference ref;
    private  FirebaseDatabase database;
    private TextView eventDescription;
    GameRequestDialog requestDialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        requestDialog = new GameRequestDialog(this);
        requestDialog.registerCallback(callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
            @Override
            public void onSuccess(GameRequestDialog.Result result) {
                String id = result.getRequestId();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        setContentView(R.layout.activity_event_info);

        eventDescription = (TextView) findViewById(R.id.eventDescription);
        final String s = getIntent().getStringExtra("eventNumber");


        database = FirebaseDatabase.getInstance();
        ref = database.getReference("TEAM19");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String description = (String) dataSnapshot.child("events").child(s).child("description").getValue();
                eventDescription.setText(description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mapButton = (Button)findViewById(R.id.MapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventInfo.this, MapsActivity.class);
                i.putExtra("eventNumber", s);
                startActivity(i);

            }
        });

        listRideButton = (Button)findViewById(R.id.listRideButton);
        listRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventInfo.this, ListRideActivity.class);
                i.putExtra("eventNumber", s);
                startActivity(i);

            }
        });

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventInfo.this, MainActivity.class));
                finish();
            }
        });
        inviteButton = (Button)findViewById(R.id.inviteButton);
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //buttonHandler();
                onClickRequestButton();
            }
        });
    }*/

  /*  public void buttonHandler() {
        final String s = getIntent().getStringExtra("eventNumber");
        String appLinkUrl, previewImageUrl;
        appLinkUrl = "https://fb.me/1033003976822219";
        previewImageUrl = "www.google.com";
        if(AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .setPromotionDetails("eventNumber", s)
                    .build();
            AppInviteDialog.show(this,content);
        }
    }*/ /*
private void onClickRequestButton() {
    // Bundle bundle = new Bundle();
    //bundle.putString("app_id", "1011343502321600");
    final String app_id = "{\"app_id\":" + "1011343502321600" + "}";
    final String event_number = "\n{\"event_number\":" + getIntent().getStringExtra("eventNumber") + "}";
    GameRequestContent content = new GameRequestContent.Builder()
            .setMessage("Join my Event!")
            .setData(app_id + event_number)
            .setActionType(GameRequestContent.ActionType.SEND)
            .setObjectId("4313")
            .build();
    requestDialog.show(content);
    //requestDialog.show(this, content);
}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

 */
