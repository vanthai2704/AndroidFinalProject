package com.example.fastjobs;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fastjobs.Adapter.CartAdapter;
import com.example.fastjobs.Adapter.RecentPostAdapter;
import com.example.fastjobs.Entity.Cart;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.PostSupport;
import com.example.fastjobs.firebase.UserSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    ListView listView;
    private UserSupport userSupport;
    private LocationManager locationManager;
    private Activity activityTmp;
    private Context contextTmp;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        activityTmp = getActivity();
        contextTmp = getContext();
        locationManager = (LocationManager) activityTmp.getSystemService(Context.LOCATION_SERVICE);
        listView = view.findViewById(R.id.listCart);

        userSupport = UserSupport.getInstance();
        userSupport.getCarts(new CallbackSupport<Cart>() {
            @Override
            public void onCallback(Cart cart, String key, List<Cart> carts) {
                if(carts == null){
                    carts = new ArrayList<>();
                }
                Location currentLocation = getLastBestLocation();
                CartAdapter cartAdapter = new CartAdapter(getCardFragment(),carts, currentLocation);
                listView.setAdapter(cartAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post postSelected = (Post)listView.getAdapter().getItem(position);
                String post_id = postSelected.getPost_id();
                ViewPostFragment viewPostFragment = ViewPostFragment.newInstance(
                        post_id, null);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contentLayout,viewPostFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private Location getLastBestLocation() {
        if (activityTmp != null && ContextCompat.checkSelfPermission(activityTmp,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activityTmp,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activityTmp,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        123);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return null;
        } else {
            // Permission has already been granted
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            long GPSLocationTime = 0;
            if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

            long NetLocationTime = 0;

            if (null != locationNet) {
                NetLocationTime = locationNet.getTime();
            }

            if ( 0 < GPSLocationTime - NetLocationTime ) {
                return locationGPS;
            }
            else {
                return locationNet;
            }
        }


    }

    public CartFragment getCardFragment(){
        return this;
    }

}
