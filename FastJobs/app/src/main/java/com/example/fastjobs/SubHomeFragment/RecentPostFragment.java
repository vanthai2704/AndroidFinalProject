package com.example.fastjobs.SubHomeFragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fastjobs.Adapter.RecentPostAdapter;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.MyPostDetail;
import com.example.fastjobs.R;
import com.example.fastjobs.ViewPostFragment;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.PostSupport;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentPostFragment extends Fragment implements LocationListener{
    ListView listView;
    private PostSupport postSupport;
    private LocationManager locationManager;
    private Activity activityTmp;
    private Context contextTmp;

    private static RecentPostFragment instance = null;
    public static RecentPostFragment getInstance(){
        if(instance == null){
            instance = new RecentPostFragment();
        }
        return instance;
    }

    public RecentPostFragment() {
        // Required empty public constructor
    }


    private FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fab = getActivity().findViewById(R.id.fab);
        View view= inflater.inflate(R.layout.fragment_recent_post, container, false);
        activityTmp = getActivity();
        contextTmp = getContext();
        locationManager = (LocationManager) activityTmp.getSystemService(Context.LOCATION_SERVICE);
        listView = view.findViewById(R.id.listViewPost);
        postSupport = PostSupport.getInstance();
        postSupport.getRecently(new CallbackSupport<Post>() {
            @Override
            public void onCallback(Post post, String key, List<Post> posts) {
                Location currentLocation = getLastBestLocation();
                RecentPostAdapter recentPostAdapter = new RecentPostAdapter(getRecentPostFragment(),posts, currentLocation);
                listView.setAdapter(recentPostAdapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(contextTmp);
                Post postSelected = (Post)listView.getAdapter().getItem(position);
                String post_id = postSelected.getPost_id();
                ViewPostFragment viewPostFragment = ViewPostFragment.newInstance(
                        post_id, null);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contentLayout,viewPostFragment);
                fragmentTransaction.commit();
                fab.hide();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public RecentPostFragment getRecentPostFragment(){
        return this;
    }

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
}
