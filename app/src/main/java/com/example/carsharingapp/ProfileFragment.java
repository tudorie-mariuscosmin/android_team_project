package com.example.carsharingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.models.RideAndCard;
import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.database.service.RideService;
import com.example.carsharingapp.database.service.UserService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.jetbrains.annotations.NotNull;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ProfileFragment extends Fragment {

    public static final String USER_KEY = "User_key";
    private User user;

    private TextView tvTitle;
    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private SwitchMaterial switchEdit;
    private RatingBar rateAppBar;
    private Button saveBtn;
    private Button deleteAccountBtn;
    private Button exportDataBtn;

    private UserService userService;

    private  List<RideAndCard> rides;
    private RideService rideService;

    View view;

    public ProfileFragment(){}

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        user =(User)getArguments().getSerializable(USER_KEY);
        userService = new UserService(getContext().getApplicationContext());
        initComponents(view);
        setUpViewWithUserData(user);
        getRides();
        switchEdit.setOnCheckedChangeListener(switchChangeListener());
        deleteAccountBtn.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setMessage(getString(R.string.delete_account_message))
                    .setTitle(R.string.delete_account)
                    .setIcon(R.drawable.ic_baseline_delete_24)
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        dialog.cancel();
                    })
                    .setPositiveButton("delete", (dialog, which) -> {
                        deleteAccount();
                    }).show();

        });

        exportDataBtn.setOnClickListener(v -> {
            View eDialog = inflater.inflate(R.layout.export_dialog, null);
            new MaterialAlertDialogBuilder(requireContext())
                    .setView(eDialog)
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .setPositiveButton("Export",(dialog, which) -> {
                        RadioGroup rg = eDialog.findViewById(R.id.rg_export_type);
                        if(rg.getCheckedRadioButtonId() == R.id.rb_export_txt){
                            saveToTxtFile(rides);
                            Snackbar.make(exportDataBtn, "Text export saved on your device", Snackbar.LENGTH_SHORT).show();
                        }else if(rg.getCheckedRadioButtonId() == R.id.rb_export_csv){
                            saveToCsvFile(rides);
                            Snackbar.make(exportDataBtn, "CSV export saved on your device", Snackbar.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(requireContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        });
        return view;
    }

    @NotNull
    private CompoundButton.OnCheckedChangeListener switchChangeListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setViewsEnabledState(true);
                    setSaveBtn();
                }else{
                    setViewsEnabledState(false);
                    setUpViewWithUserData(user);
                    removeSaveBtn();
                }
            }
        };
    }

    private void removeSaveBtn() {
        ConstraintLayout layout = view.findViewById(R.id.profile_fragment);
        layout.removeView(saveBtn);
    }

    private void setViewsEnabledState( boolean value) {
        if(value){
            tvTitle.setText(R.string.profile_page_edit);
        }else {
            tvTitle.setText(R.string.profile_page);
        }
        etName.setEnabled(value);
        etEmail.setEnabled(value);
        etPhone.setEnabled(value);
        etPassword.setEnabled(value);
        rateAppBar.setIsIndicator(!value);
    }

    private void initComponents(View view){
        tvTitle = view.findViewById(R.id.tv_profile_title);
        etName = view.findViewById(R.id.et_profile_name);
        etEmail = view.findViewById(R.id.et_profile_email);
        etPhone = view.findViewById(R.id.et_profile_phone);
        etPassword = view.findViewById(R.id.et_profile_password);
        switchEdit = view.findViewById(R.id.switch_profile_editable);
        rateAppBar = view.findViewById(R.id.rating_profile);
        deleteAccountBtn = view.findViewById(R.id.btn_profile_delete);
        exportDataBtn = view.findViewById(R.id.btn_profile_export);
    }
    private void setUpViewWithUserData(User user){
        if(!user.getName().isEmpty() || user.getName()!=null){
            etName.setText(user.getName());
        }else{
            etName.setText(R.string.empty_value);
        }

        if(user.getEmail().isEmpty() || user.getEmail()!=null){
            etEmail.setText(user.getEmail());
        }else{
            etEmail.setText(R.string.empty_value);
        }

        if(!user.getPhone().isEmpty() || user.getPhone()!=null){
            etPhone.setText(user.getPhone());
        }else{
            etPhone.setText(R.string.empty_value);
        }

        if(user.getPassword().isEmpty() || user.getPassword()!=null){
            etPassword.setText(user.getPassword());
        }else{
            etPassword.setText(R.string.empty_value);
        }

        rateAppBar.setRating(user.getRating());
    }

    private  void  setSaveBtn(){
        saveBtn = new Button(getContext().getApplicationContext());
        saveBtn.setText(R.string.save);
        saveBtn.setBackgroundColor(getContext().getResources().getColor(R.color.blue_primary));
        saveBtn.setId(View.generateViewId());
        ConstraintSet set = new ConstraintSet();
        ConstraintLayout layout = view.findViewById(R.id.profile_fragment);
        layout.addView(saveBtn);
        set.clone(layout);
        set.constrainWidth(saveBtn.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainHeight(saveBtn.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(saveBtn.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        set.connect(saveBtn.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        set.connect(saveBtn.getId(), ConstraintSet.TOP, rateAppBar.getId(), ConstraintSet.BOTTOM);
        set.applyTo(layout);
        saveBtn.setOnClickListener(v -> {
            updateUser();
        });
    }

    private void updateUser(){
        user.setName(etName.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setRating(rateAppBar.getRating());
        userService.updateUser(user, result ->{
            if(result!=null){
                switchEdit.setChecked(false);
                Snackbar.make(switchEdit, "You have updated your profile successfully", Snackbar.LENGTH_LONG).show();

            }
        });

    }

    private void deleteAccount(){
        userService.deleteUser(user, new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if(result !=-1){
                    Intent intent = new Intent(getContext().getApplicationContext(), LoginActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                }
            }
        });

    }


    private void saveToTxtFile(List<RideAndCard> rides){
        try {
            FileOutputStream fileOutputStream = getActivity().openFileOutput("export.txt", Context.MODE_PRIVATE);
            DataOutputStream outputStream = new DataOutputStream(fileOutputStream);
            outputStream.write("Your rides: \n".getBytes());
            for(RideAndCard rideAndCard : rides){
                outputStream.write((rideAndCard.toString() + "\n").getBytes());
            }

            outputStream.close();
            fileOutputStream.close();


        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToCsvFile(List<RideAndCard> rides){
        try {
            String csvSeparator = ",";
            String lineBreak = "\n";
            FileOutputStream fileOutputStream = getActivity().openFileOutput("export.csv", Context.MODE_PRIVATE);
            DataOutputStream out = new DataOutputStream(fileOutputStream);
            out.write("RideNo.".getBytes());
            out.write(csvSeparator.getBytes());
            out.write("From date".getBytes());
            out.write(csvSeparator.getBytes());
            out.write("Until date".getBytes());
            out.write(csvSeparator.getBytes());
            out.write("Car".getBytes());
            out.write(csvSeparator.getBytes());
            out.write("Location".getBytes());
            out.write(csvSeparator.getBytes());
            out.write("Expected km".getBytes());
            out.write(csvSeparator.getBytes());
            out.write("Card".getBytes());
            out.write(lineBreak.getBytes());
            for(RideAndCard rideAndCard : rides){
                out.write(String.valueOf(rideAndCard.ride.getId()).getBytes());
                out.write(csvSeparator.getBytes());
                out.write(rideAndCard.ride.getFromDate().getBytes());
                out.write(csvSeparator.getBytes());
                out.write(rideAndCard.ride.getUntilDate().getBytes());
                out.write(csvSeparator.getBytes());
                out.write(rideAndCard.ride.getCar().getBytes());
                out.write(csvSeparator.getBytes());
                out.write(rideAndCard.ride.getCar().getBytes());
                out.write(csvSeparator.getBytes());
                out.write(String.valueOf(rideAndCard.ride.getExpectedKm()).getBytes());
                out.write(csvSeparator.getBytes());
                out.write(rideAndCard.card.toString().getBytes());
                out.write(lineBreak.getBytes());
            }

            out.close();
            fileOutputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Log.d("error csv", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("error csv", e.getMessage());
        }
    }


    private void getRides(){
        rides = new ArrayList<>();
        rideService = new RideService(getContext().getApplicationContext());
        rideService.getRides(result -> {
            if(result !=null){
                rides.clear();
                rides.addAll(result);
            }
        });
    }
}