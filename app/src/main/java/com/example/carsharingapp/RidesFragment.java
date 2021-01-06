package com.example.carsharingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.carsharingapp.customAdapters.RideAdapter;
import com.example.carsharingapp.database.models.RideAndCard;
import com.example.carsharingapp.database.service.RideService;

import java.util.ArrayList;
import java.util.List;


public class RidesFragment extends Fragment {

    private List<RideAndCard> rides;
    private RideService rideService;

    GridView gvRides;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_rides, container, false);
        rides = new ArrayList<>();
        rideService = new RideService(getContext().getApplicationContext());
        rideService.getRides(result -> {
            if(result !=null){
                rides.clear();
                rides.addAll(result);
                ArrayAdapter<RideAndCard> adapter =(ArrayAdapter<RideAndCard>) gvRides.getAdapter();
                adapter.notifyDataSetChanged();
                Log.d("Rides", rides.toString());
            }
        });

        gvRides = view.findViewById(R.id.gv_rides);
        RideAdapter adapter = new RideAdapter(requireContext(), R.layout.gv_item_ride, rides, inflater);
        //ArrayAdapter<RideAndCard> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,rides );
        gvRides.setAdapter(adapter);

        return view;
    }
}