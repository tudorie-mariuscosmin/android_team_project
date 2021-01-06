package com.example.carsharingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.models.CreditCard;
import com.example.carsharingapp.database.models.Ride;
import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.database.service.CreditCardService;
import com.example.carsharingapp.database.service.RideService;
import com.example.carsharingapp.util.Car;
import com.example.carsharingapp.util.City;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    private EditText dateFrom;
    private EditText dateUntill;
    Calendar calendar = Calendar.getInstance();
    int startYear = calendar.get(Calendar.YEAR);
    int starthMonth = calendar.get(Calendar.MONTH);
    int startDay = calendar.get(Calendar.DAY_OF_MONTH);

    private List<Car> cars;
    private List<City>cities;
    private List<CreditCard> cards;

    private CreditCardService cardService;

    Spinner spnCarBrand;
    Spinner spnLocation;
    Spinner spnCreditCards;
    SeekBar seekBarKm;
    Button btnGetRide;

    SharedPreferences sharedPreferences;
    RideService rideService;

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
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cars = getArguments().getParcelableArrayList("carList");
        cities = getArguments().getParcelableArrayList("cityList");

        cards = new ArrayList<>();
        cardService = new CreditCardService(getContext().getApplicationContext());
        cardService.getAllUserCards(getUserCardsCallback());

        setSpinners(view);
        setDatePickers(view);
        setSeekBar(view);

        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        rideService = new RideService(getContext().getApplicationContext());
        btnGetRide = view.findViewById(R.id.btn_home_new_ride);
        btnGetRide.setOnClickListener(v -> {
            if(validate()){
               Ride ride =  createRide();
               rideService.insertRide(ride, result -> {
                   if(result!=null){
                       Snackbar.make(btnGetRide, "You got a new ride!", Snackbar.LENGTH_INDEFINITE).setAction("See Rides",v1 -> {
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_container, new RidesFragment()).commit();
                       }).show();
                   }
               });

            }
        });



        return view;
    }

    private DatePickerDialog.OnDateSetListener datePickerListenerfromDate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateFrom.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListeneruntillDate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateUntill.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

    @NotNull
    private Callback<List<CreditCard>> getUserCardsCallback() {
        return new Callback<List<CreditCard>>() {
            @Override
            public void runResultOnUiThread(List<CreditCard> result) {
                if(result != null){
                    cards.clear();
                    cards.addAll(result);
                    ArrayAdapter adapter = (ArrayAdapter) spnCreditCards.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }


    private void setSpinners(View view){
        spnCarBrand = view.findViewById(R.id.spn_home_carbrand);
        spnLocation = view.findViewById(R.id.spn_home_location);
        spnCreditCards = view.findViewById(R.id.spn_home_card);

        ArrayAdapter<Car> arrayAdapterCar = new ArrayAdapter<Car>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cars);
        ArrayAdapter<City> arrayAdapterCity = new ArrayAdapter<City>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        ArrayAdapter<CreditCard> cardArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cards);
        spnCarBrand.setAdapter(arrayAdapterCar);
        spnLocation.setAdapter(arrayAdapterCity);
        spnCreditCards.setAdapter(cardArrayAdapter);
    }

    private void setDatePickers(View view){
        dateFrom = view.findViewById(R.id.et_home_datefrom);
        dateFrom.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(), datePickerListenerfromDate, startYear, starthMonth, startDay);
                datePickerDialog.show();
            }
        });
        dateFrom.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(), datePickerListenerfromDate, startYear, starthMonth, startDay);
            datePickerDialog.show();

        });

        dateUntill = view.findViewById(R.id.et_home_dateuntill);
        dateUntill.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(), datePickerListeneruntillDate, startYear, starthMonth, startDay);
                datePickerDialog.show();
            }
        });
        dateUntill.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(), datePickerListeneruntillDate, startYear, starthMonth, startDay);
            datePickerDialog.show();
        });
    }

    private void setSeekBar(View view){
        seekBarKm= view.findViewById(R.id.sb_home_kilometers);
        seekBarKm.setMax(2000);
        TextView tView = view.findViewById(R.id.tv_home_seekbar);
        tView.setText("Expected: "+ seekBarKm.getProgress() + "/" + seekBarKm.getMax() + " km");
        seekBarKm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tView.setText("Expected: " + pval + "/" + seekBar.getMax() + " km");
            }
        });
    }

    private  boolean validate(){
        if(dateFrom.getText().toString().isEmpty() || dateFrom.getText()==null){
            Snackbar.make(btnGetRide, "Please select the from date", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(dateUntill.getText().toString().isEmpty() || dateUntill.getText()==null){
            Snackbar.make(btnGetRide, "Please select the until date", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if(spnCreditCards.getSelectedItem() == null){
            Snackbar.make(btnGetRide, "Please add a card", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if(seekBarKm.getProgress() == 0){
            Snackbar.make(btnGetRide, "Please select a expected no of km", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private Ride createRide(){
        String fromDate = dateFrom.getText().toString().trim();
        String endDate = dateUntill.getText().toString().trim();
        long userId = sharedPreferences.getLong(LoginActivity.USER_ID, -1 );
        String location = spnLocation.getSelectedItem().toString();
        String car = spnCarBrand.getSelectedItem().toString();
        CreditCard card =(CreditCard) spnCreditCards.getSelectedItem();
        int expectedkm = seekBarKm.getProgress();

        Ride ride = new Ride(userId, fromDate, endDate, location, car, card.getId(), expectedkm);
        return  ride;

    }




}