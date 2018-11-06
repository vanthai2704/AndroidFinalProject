package com.example.fastjobs;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback {


    Location locationA = new Location("point A");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.

        if(Geocoder.isPresent()){
            try {
                String location = "xuân mai huyện chương mỹ hà nội";
                Geocoder gc = new Geocoder(this);
                List<Address> addresses= gc.getFromLocationName(location, 5); // get ddthe found Aress Objects


                List<LatLng> ll = new ArrayList<>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                    }
                }
                locationA.setLatitude(addresses.get(0).getLatitude());
                locationA.setLongitude(addresses.get(0).getLongitude());
            } catch (IOException e) {
                // handle the exception
            }
        }
//        Location locationB = new Location("point B");
//        try {
//            String location = "Xã hòa sơn huyện lương sơn hòa bình";
//            Geocoder gc = new Geocoder(this);
//            List<Address> addresses= gc.getFromLocationName(location, 5); // get ddthe found Aress Objects
//
//
//            List<LatLng> ll = new ArrayList<>(addresses.size()); // A list to save the coordinates if they are available
//            for(Address a : addresses){
//                if(a.hasLatitude() && a.hasLongitude()){
//                    ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
//                }
//            }
//            locationB.setLatitude(addresses.get(0).getLatitude());
//            locationB.setLongitude(addresses.get(0).getLongitude());
//        } catch (IOException e) {
//            // handle the exception
//        }
//
//
//        float distance = locationA.distanceTo(locationB);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(locationA.getLatitude(), locationA.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));
    }
}
