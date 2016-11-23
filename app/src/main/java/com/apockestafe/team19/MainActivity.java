package com.apockestafe.team19;

import android.app.ListActivity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bolts.AppLinks;

import static com.facebook.internal.FacebookDialogFragment.TAG;


public class MainActivity extends AppCompatActivity {

    private ListView scrollList;
    private Button addbutton, settingsButton;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private SharedPreferencesEditor editor;
    private EventList eventList;
    private ArrayAdapter<String> adapter;
    private final ArrayList<String> aList = new ArrayList<>();
    private String email, token;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if(targetUrl != null) {
            token = getIntent().getExtras().getString("fb_access_token");
            AppLinkData ald = AppLinkData.createFromAlApplinkData(getIntent());
            String promoCode = ald.getPromotionCode();
            if(promoCode != null) {
                //put it in sharedpref.
            }
        }
        else{
            AppLinkData.fetchDeferredAppLinkData(getApplicationContext(),
                    new AppLinkData.CompletionHandler() {
                        @Override
                        public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                            String promoCode = appLinkData.getPromotionCode();
                            if(promoCode != null) {
                                //put it in sharedpref
                            }
                        }
                    });
        }
        auth = FirebaseAuth.getInstance();
      /*  user = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };*/
        /*
        try {
            System.out.println(AccessToken.getCurrentAccessToken().getToken());
        }
        catch(NullPointerException e) {
            System.out.println("aeljaejla");
        }*/
        editor = new SharedPreferencesEditor(getSharedPreferences("login", MODE_PRIVATE));


        if(editor.getMyEmail() == null || editor.getMyEmail() == ""){
            if(token == null) {
                token = AccessToken.getCurrentAccessToken().getToken();
            }
            handleFacebookAccessToken(token);
//            email = auth.getCurrentUser().getEmail();
//            editor.addMyEmail(email);
        }
        //handleFacebookAccessToken(token);
        setContentView(R.layout.activity_main);

       // editor = new SharedPreferencesEditor(getSharedPreferences("login",MODE_PRIVATE));
       // user = auth.getCurrentUser();

        /*if(editor.getMyEmail() == null || editor.getMyEmail() == "") {
            editor.addMyEmail(email);
        }*/

        addbutton = (Button) findViewById(R.id.button1);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
                finish();
            }
        });

        settingsButton = (Button) findViewById(R.id.button2);
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
                startActivity(newActivity);
            }
        });
    }


    private void handleFacebookAccessToken(String token) {
        Log.d("herehere", "handleFacebookAccessToken:" + token);
//        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        try{
//                            String email = object.getString("email");
//                            editor.addMyEmail(email);
//                            System.out.println("login Email: " + email);
//                        }
//                        catch(JSONException e){}
//                    }
//                });
//        request.executeAsync();
        AuthCredential credential = FacebookAuthProvider.getCredential(token);
        auth.signInWithCredential(credential).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             /*   email = auth.getCurrentUser().getEmail();
                if(editor.getMyEmail() == null || editor.getMyEmail() == "") {
                    editor.addMyEmail(email);
                }*/
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                if(!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential", task.getException());
                }
            }
        });
    }

    public void listAdapter () {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TEAM19");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                eventList = new EventList(dataSnapshot);
//                listAdapterHelper();
                for (DataSnapshot data : dataSnapshot.child("events").getChildren()) {
                    String title = (String) data.child("title").getValue();
                    String date = (String) data.child("date").getValue();
                    String eventDisplayName = title + " | " + date;
                    aList.add(eventDisplayName);
                }
                adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, aList);
                scrollList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

//    private void listAdapterHelper () {
//        int i;
//        int length = eventList.size();
//        for(i=0; i<length; i++) {
//            String title = eventList.get(i).getTitle();
//            String date = eventList.get(i).getDate();
//
//        }
//    }
}
