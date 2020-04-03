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
    int x = 0;
    int y = 0;
    int z = 0;
    LatLng latlng;
    String countryInfos;
    String casesInfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_tracker_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        latitudeMap = new double[latitudeItems.size()];
        for (int i = 0; i < latitudeMap.length; i++) {
            latitudeMap[i] = latitudeItems.get(i);
        }
        longitudeMap = new double[longitudeItems.size()];
        for (int i = 0; i < longitudeMap.length; i++) {
            longitudeMap[i] = longitudeItems.get(i);
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<LatLng> points = new ArrayList<LatLng>();
        List<String> detailsCountry = new ArrayList<>();
        List<String> detailsCases = new ArrayList<>();
        for (int i = 0; i < latitudeMap.length; i++) {
            points.add(new LatLng(latitudeMap[i], longitudeMap[i]));
            detailsCountry.add(new String(countryForMapItems.get(i)));
            detailsCases.add(new String(casesForMapItems.get(i)));
            if (x != 249) {
                latlng = points.get(x);
                countryInfos = detailsCountry.get(y);
                casesInfos = detailsCases.get(z);
                mMap.addMarker(new MarkerOptions().position(latlng).title(countryInfos).snippet("Cases:" + casesInfos));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                x++;
                y++;
                z++;
            }


        }
    }
}