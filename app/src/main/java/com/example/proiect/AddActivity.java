package com.example.proiect;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

   private ArrayList<LoginItem> mLoginList;
   private LoginAdapter loginAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initList();

        Spinner spinnerLogins=findViewById(R.id.spn_login);
        loginAdapter=new LoginAdapter(this,mLoginList);
        spinnerLogins.setAdapter(loginAdapter);
        spinnerLogins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoginItem clickedItem=(LoginItem) parent.getItemAtPosition(position);
                String clickedTypeName=clickedItem.getmLoginName();
                Toast.makeText(AddActivity.this,clickedTypeName+" selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initList(){
        mLoginList=new ArrayList<LoginItem>();
        mLoginList.add(new LoginItem("Client",R.drawable.client));
        mLoginList.add(new LoginItem("Driver",R.drawable.driver));


    }
}