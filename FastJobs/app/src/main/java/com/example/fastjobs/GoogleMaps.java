package com.example.fastjobs;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback {



    LatLng destinationPoint = new LatLng(0 ,0);
    LatLng startingPoint = new LatLng(0 ,0);

    private GoogleMap mMap;
    ArrayList markerPoints= new ArrayList();
    String destination="";
    String phoneNumber="";

    TextView txtDistance ;
    TextView txtDuration ;
    TextView txtDetailAddress ;
    TextView txtPhoneNumber ;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

         txtDistance = findViewById(R.id.txtDistance);
         txtDuration = findViewById(R.id.txtDuration);
         txtDetailAddress= findViewById(R.id.txtDetailAddress);
         txtPhoneNumber = findViewById(R.id.txtPhoneNumber);

        destination = this.getIntent().getStringExtra("destinationPoint");
        phoneNumber = this.getIntent().getStringExtra("phoneNumber");

        if(destination == null ){
            destination ="Trường đại học ngoại thương Hà Nội";
        }
        if(phoneNumber == null){
            phoneNumber = "0969347967";
        }


       Location current = getLastBestLocation();
       if(current!=null){
           startingPoint = new LatLng(current.getLatitude(),current.getLongitude());
       }else {
           System.out.println("Can't get current location");
       }


        // Obtain the SupportMapFragment and get notified when the map
                if(Geocoder.isPresent()){
                    try {
                        Geocoder gc = new Geocoder(getApplicationContext());
                        List<Address> addresses= gc.getFromLocationName(destination, 1);
                        destinationPoint = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                    } catch (Exception ex) {
                        // handle the exception
                        ex.printStackTrace();
                    }
                }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
                markerPoints.add(startingPoint);
                markerPoints.add(destinationPoint);
                // Checks, whether start and end locations are captured
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 15));
                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest) ;
                    DownloadTask downloadTask = new DownloadTask();
                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
    }

    private class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);

            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
          //  super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Void, List<List<HashMap<String,String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String,String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String,String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String,String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions option1 = new MarkerOptions();
            MarkerOptions option2 = new MarkerOptions();
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String,String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String,String> point = path.get(j);

                    if(j == 0){
                        // Setting the position of the marker
                        option1.position(startingPoint);
                        option1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        option1.title("Điểm Xuất Phát");
                        option1.snippet("Khoảng Cách: "+point.get("distance")+"\n");
                        txtDistance.setText("Khoảng Cách: "+point.get("distance"));
                        // Setting the position of the marker
                        option2.position(destinationPoint);
                        option2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        option2.title("Điểm Đến");
                        option2.snippet(destination + "\n" +"SĐT: " + phoneNumber );
                        txtDetailAddress.setText("Địa Chỉ: "+destination);
                        txtPhoneNumber.setText("Số Điện Thoại: "+phoneNumber);
                        continue;
                    }
                    if(j == 1){
                        option1.snippet(option1.getSnippet()+"Thời Gian: "+point.get("duration")+"\n");
                        txtDuration.setText("Thời Gian: "+point.get("duration"));
                        // Add new marker to the Google Map Android API V2
                        mMap.addMarker(option1);
                        mMap.addMarker(option2);
                        continue;
                    }
                    double lat = Double.parseDouble(point.get("lat").trim());
                    double lng = Double.parseDouble(point.get("lng").trim());
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.rgb(2,178,251));
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters +"&key="+"AIzaSyCrk6LIIuZuOElVIZP7dr2OsfRKpg62uzU";


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
     class DirectionsJSONParser  {

        private static final String TAG = "DirectionsJSONParser";

        public DirectionsJSONParser() {
            Log.d(TAG, "OBJECT IS  CREATED SUCCESSFULLY.");
        }

        public List<List<HashMap<String,String>>> parse(JSONObject jObject) {

            Log.i(TAG,"DIRECTIONSJSONParser class has started.");
            List<List<HashMap<String, String>>> routes = new ArrayList<>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;
            JSONObject jDistance = null;
            JSONObject jDuration = null;

            try {

                jRoutes = jObject.getJSONArray("routes");
                Log.d(TAG, "routes = " + jRoutes.toString());


                /** Traversing all routes */
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    Log.d(TAG, "legs = " + jLegs.toString());
                    List<HashMap<String, String>> path = new ArrayList<>();

                    /** Traversing all legs */
                    for (int j = 0; j < jLegs.length(); j++) {

                        /** Getting distance from the json data */
                        jDistance = ((JSONObject) jLegs.get(j)).getJSONObject("distance");
                        HashMap<String, String> hmDistance = new HashMap<>();
                        hmDistance.put("distance", jDistance.getString("text"));
                        Log.d(TAG, "hmDistance = " + hmDistance.toString());

                        /** Getting duration from the json data */
                        jDuration = ((JSONObject) jLegs.get(j)).getJSONObject("duration");
                        HashMap<String, String> hmDuration = new HashMap<>();
                        hmDuration.put("duration", jDuration.getString("text"));
                        Log.d(TAG, "hmDuration = " + hmDuration.toString());

                        /** Adding distance object to the path */
                        path.add(hmDistance);

                        /** Adding duration object to the path */
                        path.add(hmDuration);
                        Log.d(TAG, "PATH = " + path.toString());

                        jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                        //Traversing all steps
                        for(int k=0;k<jSteps.length();k++){
                            String polyline = "";
                            polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /// Traversing all points
                            for(int l=0;l<list.size();l++){
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                                hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                                path.add(hm);
                            }
                        }
                    }
                    routes.add(path);
                }
                Log.d(TAG, "routes in DirectionJSONParser = " + routes.toString());
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return routes;
        }
        /*
         * Method to decode polyline points
         * Courtesy : jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
         */
        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }
            return poly;
        }
    }

    private Location getLastBestLocation() {
        LocationManager locationManager=  (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (this != null && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
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
