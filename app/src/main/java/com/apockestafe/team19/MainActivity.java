package com.apockestafe.team19;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
//import com.firebase.client.Firebase;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.applinks.AppLinkData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import bolts.AppLinks;

public class MainActivity extends AppCompatActivity {

    private ListView scrollList;
    private Button addbutton, settingsButton;
    private SharedPreferencesEditor editor;
    private ArrayAdapter<String> adapter;
    private final ArrayList<String> aList = new ArrayList<>();
    private String token;
    private ArrayList<String> eNums;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("My Events");

        contextOfApplication = getApplicationContext();

        FacebookSdk.sdkInitialize(getApplicationContext());
        editor = new SharedPreferencesEditor(getSharedPreferences("login", MODE_PRIVATE));
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if(targetUrl != null) {
            token = getIntent().getExtras().getString("fb_access_token");
            AppLinkData ald = AppLinkData.createFromAlApplinkData(getIntent());
            String promoCode = ald.getPromotionCode();
            Set<String> set = editor.getEvents();
            ArrayList<String> eventNumbers = new ArrayList<>();
            if(set != null) {
                eventNumbers.addAll(set);
            }
            eventNumbers.add(promoCode);
            editor.addEvents(eventNumbers);
        }




        //handleFacebookAccessToken(token);
        setContentView(R.layout.activity_main);

        addbutton = (Button) findViewById(R.id.button1);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
                finish();
            }
        });

        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SetiingsActivity.class));
            }
        });
        scrollList = (ListView) findViewById(R.id.listView);
        listAdapter();
        scrollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(MainActivity.this, EventInfo.class);
                newActivity.putExtra("eventNumber", eNums.get((int) id));
                startActivity(newActivity);
            }
        });

        scrollList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, final long id) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete hide this event");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        SharedPreferencesEditor editor = new SharedPreferencesEditor(getSharedPreferences("login", MODE_PRIVATE));
                        Set<String> set = editor.getEvents();
                        ArrayList<String> events = new ArrayList<>();
                        events.addAll(set);
                        events.remove(position);
                        editor.deleteEvent(events, position);
//                        scrollList.setAdapter(null);
//                        listAdapter();
                        adapter.clear();
                        scrollList.setAdapter(adapter);
                        listAdapter();
                        dialog.dismiss();
                    }

                });

                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }
        });
    }

    public void listAdapter () {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TEAM19");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eNums = new ArrayList<>();
                Context applicationContext = MainActivity.getContextOfApplication();
                editor = new SharedPreferencesEditor(applicationContext.getSharedPreferences("login", MODE_PRIVATE));

                Set<String> set = editor.getEvents();
                ArrayList<String> eventNumbers = new ArrayList<>();
                if (set != null) {
                    eventNumbers.addAll(0, set);
//                    editor.deleteEvents(eventNumbers);

                    for (int i = 0; i < eventNumbers.size(); i++) {
                        String title = (String) dataSnapshot.child("events").child(eventNumbers.get(i)).child("title").getValue();
                        String date = (String) dataSnapshot.child("events").child(eventNumbers.get(i)).child("date").getValue();
                        String time = (String) dataSnapshot.child("events").child(eventNumbers.get(i)).child("time").getValue();
                        String eventDisplayName = title + " | " + date + " | " + time;
                        aList.add(eventDisplayName);
                        eNums.add(eventNumbers.get(i));
                    }
                }
                adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, aList);
                scrollList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
}
