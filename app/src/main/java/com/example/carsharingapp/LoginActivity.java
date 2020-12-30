package com.example.carsharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.models.User;
import com.example.carsharingapp.database.service.UserService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_SHARED_PREF = "LoginSharedPref";
    public static final String USER_ID = "UserId";
    public static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String USER_NAME = "UserName";
    private TextView createAccount;
    private TextInputEditText tietEmail;
    private  TextInputEditText tietPassword;
    private Button btnLogin;
    private UserService userService;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        userService = new UserService(getApplicationContext());
        sharedPreferences = getSharedPreferences(LOGIN_SHARED_PREF, MODE_PRIVATE);

        createAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            if(validateComponents()){
                loginUser();

            }
        });

    }

    private void initComponents(){
        createAccount = findViewById(R.id.tv_login_create_account);
        tietEmail = findViewById(R.id.tiet_login_email);
        tietPassword = findViewById(R.id.tiet_login_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    private boolean validateComponents(){
        if(tietEmail.getText().toString().isEmpty() || tietPassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.enter_credentials_msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!tietEmail.getText().toString().contains("@")){
            Toast.makeText(getApplicationContext(), R.string.not_a_valid_email_msg, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginUser(){
        userService.findUserByEmail(tietEmail.getText().toString().trim(),result -> {
            if(result!=null){
                if(result == null){
                    Snackbar.make(btnLogin, "Incorrect Credentials", Snackbar.LENGTH_SHORT).show();
                }else{
                    if(result.getPassword().equals(tietPassword.getText().toString())){
                        setSharedPreferences(result);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        } );
    }

    private void setSharedPreferences(User user){
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        editor.putLong(USER_ID, user.getId());
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_NAME, user.getName());
        editor.apply();

    }



}