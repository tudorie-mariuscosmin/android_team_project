package com.example.carsharingapp.customAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.carsharingapp.R;
import com.example.carsharingapp.database.models.CreditCard;
import com.example.carsharingapp.util.DateConverter;

import java.util.Date;
import java.util.List;

public class CreditCardsAdapers extends ArrayAdapter<CreditCard> {
    private Context context;
    private int resource;
    private List<CreditCard> cards;
    private LayoutInflater inflater;

    public CreditCardsAdapers(@NonNull Context context, int resource, @NonNull List<CreditCard> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.cards = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view = inflater.inflate(resource, parent,false);
       CreditCard card = cards.get(position);
       if(card !=null){
           setCardholderName(view, card.getCardholderName());
           setCardNumber(view, card.getCardNumber());
           setExpirationDate(view, card.getExpiration());
           setCvv(view, card.getCvv());
           setType(view, card.getType());
       }
       return view;
    }

    private void setCardholderName(View view, String name){
        TextView textView = view.findViewById(R.id.tv_lvi_credit_card_cardholder_name);
        if(name!=null && !name.isEmpty()){
            String value = context.getString(R.string.cardholder, name);
            textView.setText(value);
        }else{
            textView.setText(R.string.empty_value);
        }
    }

    private void setCardNumber(View view, long number){
        TextView textView = view.findViewById(R.id.tv_lvi_credit_card_number);
        String cardNumber = String.valueOf(number);
        String value = context.getString(R.string.lvi_credit_card_number_format,
                cardNumber.substring(0,4), cardNumber.substring(4,8),
                cardNumber.substring(8,12),cardNumber.substring(12,16));
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.empty_value);
        }

    }

    private void setExpirationDate(View view, Date date){
        TextView textView = view.findViewById(R.id.tv_lvi_credit_card_expiration);
        DateConverter converter = new DateConverter();
        if(date!=null){
            String value = converter.toString(date);
            if (value != null && !value.isEmpty()) {
                textView.setText(value);
            } else {
                textView.setText(R.string.empty_value);
            }
        }

    }

    private void setCvv(View view, int cvv){
        TextView textView = view.findViewById(R.id.tv_lvi_credit_card_cvv);
        String value = String.valueOf(cvv);
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.empty_value);
        }
    }

    private void setType(View view, String type){
        ImageView imageView = view.findViewById(R.id.iv_lvi_credit_card_type);
        if(type.equals("Visa")){
            imageView.setImageResource(R.drawable.ic_visa);
        }else{
           imageView.setImageResource(R.drawable.ic_mastercard);
        }
    }
}
