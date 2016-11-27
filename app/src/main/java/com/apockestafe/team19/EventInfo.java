package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EventInfo extends AppCompatActivity {

   // private Button mapButton, listRideButton, backButton;
    private Button mapButton, listRideButton, backButton, inviteButton, itemsButton;
    private DatabaseReference ref;
    private  FirebaseDatabase database;
    private TextView eventDescription;
    private ListView friendsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        eventDescription = (TextView) findViewById(R.id.eventDescription);
        final String s = getIntent().getStringExtra("eventNumber");

        friendsListView = (ListView) findViewById(R.id.friendsListView);


        database = FirebaseDatabase.getInstance();
        ref = database.getReference("TEAM19");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String description = (String) dataSnapshot.child("events").child(s).child("description").getValue();
                eventDescription.setText("Event Description\n" + description);
                setTitle((String) dataSnapshot.child("events").child(s).child("title").getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        itemsButton = (Button) findViewById(R.id.itemsButton);
        itemsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventInfo.this, ItemsActivity.class);
                i.putExtra("eventNumber", s);
                startActivity(i);
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
                buttonHandler();
            }
        });
    }

    public void buttonHandler() {
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
    }
}
