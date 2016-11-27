package com.apockestafe.team19;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button backButton;
    private String eventAddress;
    private SharedPreferencesEditor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final String key = getIntent().getStringExtra("eventNumber");

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, EventInfo.class);
                i.putExtra("eventNumber", key);
                startActivity(i);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final Context maps = getApplicationContext();
        eventAddress = "Hey";
        mMap = googleMap;

        final DatabaseReference ref;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String s = getIntent().getStringExtra("eventNumber");

        ref = database.getReference("TEAM19/events/" + s);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ea = dataSnapshot.child("location").getValue(String.class);
                setEventAddress(ea);
                LatLng eventLocation;
                eventLocation = getLocationFromAddress(maps, eventAddress);
                float zoomLevel = (float) 17.0;
                GenericTypeIndicator<List<RideInfo>> t = new GenericTypeIndicator<List<RideInfo>>() {};
                List<RideInfo> rideInfo = dataSnapshot.child("rideLocation").getValue(t);
                if (rideInfo != null) {
                    for (int i = 0; i < rideInfo.size(); i++) {
                        LatLng ll = getLocationFromAddress(maps, rideInfo.get(i).getCarAddress());
                        if (ll != null) {
                            mMap.addMarker(new MarkerOptions().position(ll).title(rideInfo.get(i).getCarAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                        }
                    }
                }

                mMap.addMarker(new MarkerOptions().position(eventLocation).title("Event Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, zoomLevel));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
            if (!(marker.getTitle().equals("Event Location"))) {
                editor = new SharedPreferencesEditor(getSharedPreferences("marker", MODE_PRIVATE));
                editor.addMarker(marker.getPosition());
                Intent intent = new Intent(MapsActivity.this, Ride.class);
                intent.putExtra("Marker Name", marker.getTitle() + ":" + s);
                startActivity(intent);
            }
            return false;
            }
        });
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng latlng = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            latlng = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return latlng;
    }

    public void setEventAddress(String ea) {
        eventAddress = ea;
    }


}