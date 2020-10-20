package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    Car car=new Car("Dacia","Logan","Gas",2012,2000,5,50000,"Blue");
    Driver driver=new Driver("Ionut","Cercel",35,15,"B2");
    Client client=new Client("Petru","Ionescu","0712345678",10, (float) 4.75);

}
