package com.example.testmap;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public MapView mapView;
    public GoogleMap googleMap;

    public HashMap<Double, Double> owner_places_hashmap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 20);
            return;
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 20);
            return;
        }

        if(googleMap != null) { //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            googleMap.clear();

            googleMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);

            Log.i("test_response", "location.getLatitude() : "+location.getLatitude());
            Log.i("test_response", "location.getLongitude() : "+location.getLongitude());

            owner_places_hashmap.put(21.170240, 72.831062);
            owner_places_hashmap.put(21.216820, 72.830269);
            owner_places_hashmap.put(21.227830, 72.860000);
            owner_places_hashmap.put(21.238887, 72.850289);
            owner_places_hashmap.put(21.248828, 72.840899);



            if (location != null) {
                LatLng current_my_location = new LatLng(location.getLatitude(), location.getLongitude());

                owner_places_hashmap.entrySet().forEach(entry -> {
                    LatLng set_owner_places_location = new LatLng(entry.getKey(), entry.getValue());
                    googleMap.addMarker(new MarkerOptions().position(set_owner_places_location).title("Custom Location")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                });

                googleMap.addMarker(new MarkerOptions().position(current_my_location).title("My Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_my_location, 11));
            }
        }

        try {
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker arg0) {
                    if (arg0 != null) ; // if marker  source is clicked
                    Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast

                    new AlertDialog.Builder(MapsActivity.this)
                            .setTitle("" + arg0.getTitle())
                            .setMessage("Book your place")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    dialog.dismiss();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_menu_mylocation)
                            .show();
                    return true;
                }

            });
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
