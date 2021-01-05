package com.example.carsharingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.util.Car;
import com.example.carsharingapp.util.City;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private EditText dateFrom;
    private EditText dateUntill;
    int startYear = 2021;
    int starthMonth = 1;
    int startDay = 1;

    private List<Car> cars;
    private List<City>cities;

    Spinner spnCarBrand;
    Spinner spnLocation;

    public HomeFragment(){}

    public static HomeFragment newInstance(ArrayList<Car> cars, ArrayList<City> cities) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("carList", cars);
        args.putParcelableArrayList("cityList", cities);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cars = getArguments().getParcelableArrayList("carList");
        cities = getArguments().getParcelableArrayList("cityList");

        spnCarBrand = view.findViewById(R.id.spn_home_carbrand);
        spnLocation = view.findViewById(R.id.spn_home_location);

        ArrayAdapter<Car> arrayAdapterCar = new ArrayAdapter<Car>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cars);
        ArrayAdapter<City> arrayAdapterCity = new ArrayAdapter<City>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cities);

        spnCarBrand.setAdapter(arrayAdapterCar);
        spnLocation.setAdapter(arrayAdapterCity);



        // Inflate the layout for this fragment

        dateFrom = view.findViewById(R.id.et_home_datefrom);
        dateFrom.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(), datePickerListenerfromDate, startYear, starthMonth, startDay);
            datePickerDialog.show();

        });

        dateUntill = view.findViewById(R.id.et_home_dateuntill);
        dateUntill.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(), datePickerListeneruntillDate, startYear, starthMonth, startDay);
            datePickerDialog.show();
        });


        SeekBar sBar = view.findViewById(R.id.sb_home_kilometers);
        sBar.setMax(2000);
        TextView tView = view.findViewById(R.id.tv_home_seekbar);
        tView.setText("Expected: "+ sBar.getProgress() + "/" + sBar.getMax() + " km");
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tView.setText("Expected: " + pval + "/" + seekBar.getMax() + " km");
            }
        });

        return view;
    }

    private DatePickerDialog.OnDateSetListener datePickerListenerfromDate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            int day = selectedDay;
            int  month = selectedMonth;
            int year = selectedYear;
            dateFrom.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListeneruntillDate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            int day = selectedDay;
            int  month = selectedMonth;
            int year = selectedYear;
            dateUntill.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };




}