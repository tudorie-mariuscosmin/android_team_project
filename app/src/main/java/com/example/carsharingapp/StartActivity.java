package com.example.carsharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telecom.Call;
import android.telecom.Connection;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.carsharingapp.asyncTask.AsyncTaskRunner;
import com.example.carsharingapp.asyncTask.Callback;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class StartActivity extends AppCompatActivity {
    private AsyncTaskRunner asyncTaskRunner;
    private ImageView imageView;
    private boolean isLoggedIn;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        asyncTaskRunner = new AsyncTaskRunner();
        imageView = findViewById(R.id.img_start);
        progressBar = findViewById(R.id.progressbar_start);
        getImgFromWeb("https://freesvg.org/img/1541950019.png");
        checkLoggedIn();
        progressBar.setMax(150);
        Thread thread = new Thread(){
            @Override
            public void run() {
                for(int i = 0; i<150; i++){
                    try {
                        sleep(10);
                    }catch (InterruptedException ex){
                        Log.e("StartActivity", ex.getMessage());
                    }
                    progressBar.setProgress(i);
                }

                changeActivity();
            }
        };
        thread.start();

    }

    private void getImgFromWeb( String urlAdress){
        Callable<Bitmap> callable = new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                URL url = new URL(urlAdress);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            }
        };
        asyncTaskRunner.executeAsync(callable, result -> {
            if(result != null){
                imageView.setImageBitmap(result);
            }
        });
    }

    private void checkLoggedIn(){
        sharedPreferences = getSharedPreferences(LoginActivity.LOGIN_SHARED_PREF, MODE_PRIVATE);
        isLoggedIn =  sharedPreferences.getBoolean(LoginActivity.IS_LOGGED_IN, false);
    }

    private void changeActivity(){
        Intent intent;
        if(isLoggedIn){
             intent = new Intent(getApplicationContext(), MainActivity.class);
        }else{
             intent  = new Intent(getApplicationContext(), LoginActivity.class);
        }

        finish();
        startActivity(intent);
    }




}