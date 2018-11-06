package com.example.fastjobs;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.fastjobs.Entity.Category;
import com.example.fastjobs.Entity.Commune;
import com.example.fastjobs.Entity.District;
import com.example.fastjobs.Entity.Image;
import com.example.fastjobs.Entity.Post;
import com.example.fastjobs.Entity.Province;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.CategorySupport;
import com.example.fastjobs.firebase.CommuneSupport;
import com.example.fastjobs.firebase.DistrictSupport;
import com.example.fastjobs.firebase.PostSupport;
import com.example.fastjobs.firebase.ProvinceSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends android.support.v4.app.Fragment {
    private LocationManager locationManager;
    private EditText editTextDistanceSearch;
    private Spinner spinnerCategoriesSearch;
    private Button buttonSearch;
    private ListView listViewSearchJobs;
    private CategorySupport categorySupport;
    private ProvinceSupport provinceSupport;
    private DistrictSupport districtSupport;
    private CommuneSupport communeSupport;
    private PostSupport postSupport;
    private double distanceSearch;

    private Post postSearch;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Tìm Kiếm");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        postSearch = new Post();
        categorySupport = new CategorySupport();
        provinceSupport = new ProvinceSupport();
        districtSupport = new DistrictSupport();
        communeSupport = new CommuneSupport();
        postSupport = new PostSupport();
        editTextDistanceSearch = view.findViewById(R.id.editTextDistanceSearch);
        spinnerCategoriesSearch = view.findViewById(R.id.spinnerCategoriesSearch);
        buttonSearch =view.findViewById(R.id.buttonSearch);
        listViewSearchJobs = view.findViewById(R.id.listViewSearchJobs);
        categorySupport.getAll(new CallbackSupport<Category>() {

            @Override
            public void onCallback(Category category, String key, List<Category> categories) {
                ArrayAdapter<Category> adapter;
                adapter = new ArrayAdapter<Category>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        categories
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoriesSearch.setAdapter(adapter);
                spinnerCategoriesSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        postSearch.setCategory_id(((Category)parent.getItemAtPosition(position)).getCategory_id());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceSearch = Double.parseDouble(editTextDistanceSearch.getText().toString());
                loadData();
            }
        });


    }

    private void loadData(){
        postSupport.search(postSearch, 1, 100, new CallbackSupport<Post>() {
            @Override
            public void onCallback(Post post, String key, List<Post> posts) {
                List<Post> postsResult = new ArrayList<>();
                if(distanceSearch > 0){
                    for(Post p : posts){
                        double distance = -1;
                        Location location = new Location("postLocation");
                        location.setLatitude(Double.parseDouble(p.getLocation_coordinate().split("-")[0]));
                        location.setLongitude(Double.parseDouble(p.getLocation_coordinate().split("-")[1]));
                        if(getLastBestLocation() != null){
                            distance = getLastBestLocation().distanceTo(location);
                        }
                        if(distance != -1 && distance <= distanceSearch*1000){
                            postsResult.add(p);
                        }
                    }
                    ArrayAdapter<Post> postArrayAdapter
                            = new ArrayAdapter<Post>(getContext(), android.R.layout.simple_list_item_1, postsResult);
                    listViewSearchJobs.setAdapter(postArrayAdapter);
                }
            }
        });
    }

    private Location getLastBestLocation() {
        if (getActivity() != null && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        123);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return null;
        } else {
            // Permission has already been granted
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
