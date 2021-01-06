package com.example.carsharingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.util.City;
import com.example.carsharingapp.util.CityJSONParser;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;


public class MapFragment extends Fragment {
    private GoogleMap gMap;
    private String json;
    private List<City> cities;

    public MapFragment(){}
    public static MapFragment newInstance(String s) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("json", s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        json = getArguments().getString("json");
        cities = CityJSONParser.fromJson(json);

        View view=inflater.inflate(R.layout.fragment_map, container, false);
        //Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //When map loaded
                gMap=googleMap;
                gMap.getUiSettings().setZoomControlsEnabled(true);

                for(City city : cities){
                    LatLng coord = new LatLng(city.getLatitude(), city.getLongitude());
                    gMap.addMarker(new MarkerOptions().position(coord).title(city.getName()));
                }

                gMap.animateCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(44.439663, 	26.096306), 6));

//
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //When click on map
                        //Initialize marker
                        MarkerOptions markerOptions=new MarkerOptions();
                        //Set position of marker
                        markerOptions.position(latLng);
                        //Title of marker
                        markerOptions.title(latLng.latitude+" : "+ latLng.longitude);
                        //Remove marker
                        googleMap.clear();
                        //Animation for zoom
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        //Add marker
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });


        //Return view
        return view;
    }
}