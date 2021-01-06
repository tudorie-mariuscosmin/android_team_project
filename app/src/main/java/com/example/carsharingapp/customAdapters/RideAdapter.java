package com.example.carsharingapp.customAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carsharingapp.R;
import com.example.carsharingapp.database.models.CreditCard;
import com.example.carsharingapp.database.models.RideAndCard;

import java.util.List;

public class RideAdapter extends ArrayAdapter<RideAndCard> {

    private Context context;
    private int resource;
    private List<RideAndCard> rides;
    private LayoutInflater inflater;
    public RideAdapter(@NonNull Context context, int resource, @NonNull List<RideAndCard> objects, LayoutInflater inflater ) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.rides = objects;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent,false);
        RideAndCard ride = rides.get(position);
        if(ride!=null){
            setFromDate(view, ride.ride.getFromDate());
            setEndDate(view, ride.ride.getUntilDate());
            setCar(view, ride.ride.getCar());
            setCreditCard(view, ride.card.get(0).toString());
            setLocation(view, ride.ride.getLocation());
            setkm(view, ride.ride.getExpectedKm());

        }
        return view;
    }


    private void setFromDate(View view, String date){
        TextView textView = view.findViewById(R.id.tv_gvi_ride_from_date);
        if(date!=null && !date.isEmpty()){
            textView.setText(date);
        }else{
            textView.setText(R.string.empty_value);
        }
    }

    private void setEndDate(View view, String date){
        TextView textView = view.findViewById(R.id.tv_gvi_ride_until_date);
        if(date!=null && !date.isEmpty()){
            textView.setText(date);
        }else{
            textView.setText(R.string.empty_value);
        }
    }

    private void setCar(View view, String car){
        TextView textView = view.findViewById(R.id.tv_gvi_ride_car);
        if(car!=null && !car.isEmpty()){
            textView.setText(car);
        }else{
            textView.setText(R.string.empty_value);
        }
    }
    private void setCreditCard(View view, String card){
        TextView textView = view.findViewById(R.id.tv_gvi_ride_card);
        if(card!=null && !card.isEmpty()){
            textView.setText(card);
        }else{
            textView.setText(R.string.empty_value);
        }
    }

    private void setLocation(View view, String location){
        TextView textView = view.findViewById(R.id.tv_gvi_location);
        if(location!=null && !location.isEmpty()){
            textView.setText(location);
        }else{
            textView.setText(R.string.empty_value);
        }
    }

    private void setkm(View view, int km){
        TextView textView = view.findViewById(R.id.tv_gvi_km);
        String value = String.valueOf(km);
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.empty_value);
        }
    }
}
