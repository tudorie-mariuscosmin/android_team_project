package com.example.carsharingapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.database.service.UserService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.jetbrains.annotations.NotNull;

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
}