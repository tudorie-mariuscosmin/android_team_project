package com.example.carsharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.database.service.UserService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {
    private TextView termsAndConditionsDialog;
    private Button btnRegister;
    private TextInputEditText tietName;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPhone;
    private TextInputEditText tietPassword;
    private CheckBox cbTermsAndConditions;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userService = new UserService(getApplicationContext());
        initComponents();
        termsAndConditionsDialog.setOnClickListener(v -> {
            new  MaterialAlertDialogBuilder(this).setTitle("title")
                    .setMessage("Message").show();
        });

        btnRegister.setOnClickListener(v -> {
            if(validate()){
                createUser();
            }
        });
    }


    private void initComponents(){
        termsAndConditionsDialog = findViewById(R.id.tv_register_terms_and_conditions);
        btnRegister = findViewById(R.id.btn_register);
        tietName = findViewById(R.id.tiet_register_name);
        tietEmail = findViewById(R.id.tiet_register_email);
        tietPhone = findViewById(R.id.tiet_register_phone);
        tietPassword = findViewById(R.id.tiet_register_password);
        cbTermsAndConditions = findViewById(R.id.cb_register_terms_and_conditions);

    }

    private boolean validate(){
        if(tietName.getText().toString().trim().length()<3){
            Toast.makeText(getApplicationContext(), "Enter a valid name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!tietEmail.getText().toString().trim().contains("@")){
            Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tietPhone.getText().toString().trim().length()!=10){
            Toast.makeText(getApplicationContext(), "Enter a valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tietPassword.getText().toString().trim().length()<3){
            Toast.makeText(getApplicationContext(), "Enter a valid password larger than 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!cbTermsAndConditions.isChecked()){
            Toast.makeText(getApplicationContext(), "Agree to our terms and conditions", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createUser(){
        String name = tietName.getText().toString();
        String email = tietEmail.getText().toString();
        String phone = tietPhone.getText().toString();
        String password = tietPassword.getText().toString();
        User user = new User(name, email, phone, password);
        userService.insertUser(inserUserCallback(), user);

    }

    @NotNull
    private Callback<User> inserUserCallback() {
        return new Callback<User>() {
            @Override
            public void runResultOnUiThread(User result) {
                if(result==null){
                    Snackbar.make(btnRegister, R.string.user_inser_error_msg, Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(btnRegister, R.string.user_inser_success_msg, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.login_btn, v -> {
                                finish();
                            }).show();
                }
            }
        };
    }


}