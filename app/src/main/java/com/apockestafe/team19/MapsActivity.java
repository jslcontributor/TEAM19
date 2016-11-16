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

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button backButton;
    private Event e;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        mMap = googleMap;

        LatLng rideLocation = new LatLng(32.881706, -117.232939);
        LatLng eventLocation;
        eventLocation = getLocationFromAddress(this, "3831 Van Dyke Ave, San Diego, CA, 92105");
        String markerPicture = "car.ico";



        float zoomLevel = (float) 18.0;
        String[] carAddresses = getCarLocations();
//        ArrayList<String> cA = e.getRideLocation(); // Used in real life
//        for (int j = 0; j < cA.size(); j++) {
//            LatLng cL;
//            cL = getLocationFromAddress(this, cA.get(j));
//            mMap.addMarker(new MarkerOptions().position(cL).title("Car Location"));
//        }

        for (int i = 0; i < carAddresses.length; i++) {
            LatLng carLocation;
            carLocation = getLocationFromAddress(this, carAddresses[i]);
            if (carLocation != null)
                mMap.addMarker(new MarkerOptions().position(carLocation).title("Car Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));

        }
        mMap.addMarker(new MarkerOptions().position(eventLocation).title("Event Location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, zoomLevel));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle().equals("Car Location")) {
                    startActivity(new Intent(MapsActivity.this, Ride.class));
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

    public String[] getCarLocations() {
        String[] carAddresses = new String[3];
        carAddresses[0] = "21 Edendale St, Ladera Ranch, CA, 92694";
        carAddresses[1] = "3853 Van Dyke Ave, San Diego, CA, 92105";
        carAddresses[2] = "2915 Estancia, San Clemente, CA, 92670";
        return carAddresses;
    }

}