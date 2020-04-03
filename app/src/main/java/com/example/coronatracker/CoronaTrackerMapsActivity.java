package com.example.coronatracker;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CoronaTrackerMapsActivity extends MainActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double[] latitudeMap;
    double[] longitudeMap;
    int x =0;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_tracker_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        latitudeMap = new double[latitudeItems.size()];
        for (int i = 0; i < latitudeMap.length; i++) {
            latitudeMap[i] = latitudeItems.get(i).doubleValue();  // java 1.4 style
            // or:
            latitudeMap[i] = latitudeItems.get(i);                // java 1.5+ style (outboxing)
        }
        longitudeMap = new double[longitudeItems.size()];
        for (int i = 0; i < longitudeMap.length; i++) {
            longitudeMap[i] = longitudeItems.get(i).doubleValue();  // java 1.4 style
            // or:
            longitudeMap[i] = longitudeItems.get(i);                // java 1.5+ style (outboxing)
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<LatLng> points=new ArrayList<LatLng>();
        String country = TextUtils.join("-", listItems);
        for (int i = 0 ; i < latitudeMap.length; i++){
            points.add(new LatLng(latitudeMap[i],longitudeMap[i]));
           if (x != 249 ){
               LatLng latlng = points.get(x);
            mMap.addMarker(new MarkerOptions().position(latlng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            x++;

           }


        }




  //      LatLng sydney = new LatLng(-34, 151);
   //     mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

    }
}
