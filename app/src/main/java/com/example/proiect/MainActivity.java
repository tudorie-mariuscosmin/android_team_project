package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private Button btnAdd;
    private Button btnAddReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd=findViewById(R.id.btn_login);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);

            }
        });

        btnAddReg=findViewById((R.id.btn_register));
        btnAddReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

        };
    }
    //Car car=new Car("Dacia","Logan","Gas",2012,2000,5,50000,"Blue");
    //Driver driver=new Driver("Ionut","Cercel",35,15,"B2");
    //Client client=new Client("Petru","Ionescu","0712345678",10, (float) 4.75);


