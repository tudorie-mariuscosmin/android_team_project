package com.example.proiect;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private ArrayList<RegisterItem> registerAdapters;
    private RegisterAdapter mAdapterReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initList();

        Spinner spinnerRegistratios=findViewById(R.id.spinner_reg);
    mAdapterReg=new RegisterAdapter(this, registerAdapters);
    spinnerRegistratios.setAdapter(mAdapterReg);
    spinnerRegistratios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            RegisterItem clickedItem=(RegisterItem)parent.getItemAtPosition(position);
            String clickedClientName=clickedItem.getmRegisterName();
            Toast.makeText(Register.this,clickedClientName+" selected",Toast.LENGTH_SHORT);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
}


private void initList(){
    registerAdapters=new ArrayList<RegisterItem>();
    registerAdapters.add(new RegisterItem("Client",R.drawable.client));
    registerAdapters.add(new RegisterItem("Driver",R.drawable.driver));
    }
}
