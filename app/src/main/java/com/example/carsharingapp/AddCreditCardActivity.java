package com.example.carsharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsharingapp.database.models.CreditCard;
import com.example.carsharingapp.util.DateConverter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class AddCreditCardActivity extends AppCompatActivity {
    public static final String CREDIT_CARD_KEY = "creditCardKey";
    public static final int RESULT_OK = 200;

    private TextInputEditText tietCardholderName;
    private TextInputEditText tietCardNumber;
    private  TextInputEditText tietExpiration;
    private TextInputEditText tietCvv;
    private RadioGroup rgType;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    private CreditCard card;
    private  Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        initComponents();
        intent = getIntent();
        if(intent.hasExtra(CREDIT_CARD_KEY)){
            card =(CreditCard) intent.getSerializableExtra(CREDIT_CARD_KEY);
            TextView title = findViewById(R.id.tv_add_credit_card_title);
            title.setText(R.string.edit_card_title);
            setViewsContent(card);
        }
        sharedPreferences = getSharedPreferences(LoginActivity.LOGIN_SHARED_PREF, MODE_PRIVATE);
        btnSave.setOnClickListener(v -> {
            if (validate()){
                 createCreditCardFromComponents();
                   intent.putExtra(CREDIT_CARD_KEY, card);
                   setResult(RESULT_OK, intent);
                   finish();

            }
        });
    }

    private  void setViewsContent(CreditCard card){
        if(card != null){
            tietCardholderName.setText(card.getCardholderName());
            tietCardNumber.setText(String.valueOf(card.getCardNumber()));
            DateConverter converter = new DateConverter();
            tietExpiration.setText(converter.toString(card.getExpiration()));
            tietCvv.setText(String.valueOf(card.getCvv()));
            int idType;
            if(card.getType().equals("Visa")){
                idType = R.id.rb_add_credit_card_visa;
            }else{
                idType = R.id.rb_add_credit_card_mastercard;
            }
            rgType.check(idType);
        }
    }

    private void initComponents(){
        tietCardholderName =findViewById(R.id.tiet_add_credit_card_cardholder);
        tietCardNumber = findViewById(R.id.tiet_add_credit_card_number);
        tietExpiration = findViewById(R.id.tiet_add_credit_card_expiration_date);
        tietCvv = findViewById(R.id.tiet_add_credit_card_CVV);
        rgType = findViewById(R.id.rg_add_credit_card_type);
        btnSave = findViewById(R.id.btn_add_credit_card);
    }

    private boolean validate(){

        if(tietCardholderName.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a cardholder name!", Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(tietCardNumber.getText().toString().length() != 16){
            Toast.makeText(getApplicationContext(), "Please enter a valid 16 digit card name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Integer.parseInt(tietExpiration.getText().toString().split("/")[0]) >12 ||
                Integer.parseInt(tietExpiration.getText().toString().split("/")[1]) < 21
        ){
            Toast.makeText(getApplicationContext(), "Please enter a valid expiration date!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tietCvv.getText().toString().length() != 3){
            Toast.makeText(getApplicationContext(), "Please enter a valid CVV", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(rgType.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Please select a card type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createCreditCardFromComponents(){
        String cardholderName = tietCardholderName.getText().toString();
        long cardNumber = Long.parseLong( tietCardNumber.getText().toString());
        int cvv = Integer.parseInt(tietCvv.getText().toString());
        long userId = sharedPreferences.getLong(LoginActivity.USER_ID, -1 );
        DateConverter converter = new DateConverter();
        Date expirationDate = converter.fromString(tietExpiration.getText().toString());
        String type;
        int rgCheckedId = rgType.getCheckedRadioButtonId();
        if(rgCheckedId == R.id.rb_add_credit_card_visa){
            type = "Visa";
        }else{
            type = "Mastercard";
        }
        if(userId != -1 || expirationDate != null){
            if(card == null){
                card =  new CreditCard(userId,cardholderName, cardNumber, expirationDate, cvv, type );
            }else{
                card.setCardholderName(cardholderName);
                card.setCardNumber(cardNumber);
                card.setExpiration(expirationDate);
                card.setCvv(cvv);
                card.setType(type);
            }

        }

    }


}