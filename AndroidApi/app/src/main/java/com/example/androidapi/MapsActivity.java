package com.example.androidapi;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {
    SupportMapFragment mapFragment;
    FusedLocationProviderClient myLocationClient;
    boolean trackingFlag;
    LocationCallback myCallback;
    public static final String MYKEY = "my_tracking_location";
    double currentLatitude, currentLongitude;

    GoogleMap gmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        myLocationClient = LocationServices.getFusedLocationProviderClient(this);


        if(savedInstanceState != null)
            trackingFlag = savedInstanceState.getBoolean(MYKEY);

        /* This is the callback that is triggered when the
          FusedLocationClient updates your location.*/
        myCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(trackingFlag)
                {
                    new GetAddressTask(MapsActivity.this).execute(locationResult.getLastLocation());
                }
            }
        };
        checkLocationPermission();

    }

    private void checkLocationPermission()
    {
        if(!trackingFlag)
            startTrackingLocation();
        else
            stopTrackingLocation();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(MYKEY,trackingFlag);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==1)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                startTrackingLocation();
            }
            else
            {
                Toast.makeText(this,"Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startTrackingLocation()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            trackingFlag = true;
            Toast.makeText(this,"Location Permission Granted",Toast.LENGTH_SHORT).show();

            // requests periodic location updates
            myLocationClient.requestLocationUpdates(getLocationRequest(),myCallback, null);

        }
    }
    private void stopTrackingLocation()
    {
        if(trackingFlag)
            trackingFlag = false;
    }


    private LocationRequest getLocationRequest()
    {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000); // Set the desired interval for active location updates, in milliseconds
        locationRequest.setFastestInterval(5000);// interval of 5000 milliseconds (5 seconds), causes the fused location provider to return location updates that are accurate to within a few feet.
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }



    public  class GetAddressTask extends AsyncTask<Location, Void, String>
    {
        Context ct;
        GetAddressTask(Context ct)
        {
            this.ct =ct;
        }
        @Override
        protected String doInBackground(Location... locations) {

            Location location = locations[0];
            String resultMessage = "";

            if (location != null) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                resultMessage = "done";
            }


            return resultMessage;
        }


        @Override
        protected void onPostExecute(String s) {
            if (!s.equals(""))
            { mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if(trackingFlag)
                    {
                        gmap = googleMap;
                        LatLng mylatlng = new LatLng(currentLatitude, currentLongitude);
                        gmap.moveCamera(CameraUpdateFactory.newLatLng(mylatlng));
                        gmap.setMyLocationEnabled(true);

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(mylatlng);
                        markerOptions.title("You are here");

                        gmap.addMarker(markerOptions);

                    }
                }
            });
            }

        }
    }
}