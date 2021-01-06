package com.example.carsharingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view

        View view=inflater.inflate(R.layout.fragment_map, container, false);
        //Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //When map loaded
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