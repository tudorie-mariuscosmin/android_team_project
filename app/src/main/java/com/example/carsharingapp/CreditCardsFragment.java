package com.example.carsharingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.customAdapters.CreditCardsAdapers;
import com.example.carsharingapp.database.models.CreditCard;
import com.example.carsharingapp.database.service.CreditCardService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CreditCardsFragment extends Fragment {
        public static final int ADD_CREDIT_CARD_REQ = 342;
        private List<CreditCard> creditCards;
        ListView lvCreditCards;
        CreditCardService cardService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_credit_cards, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab_cred_card_add);
        creditCards = new ArrayList<>();
        lvCreditCards = view.findViewById(R.id.lv_cred_cards);
        CreditCardsAdapers adaper = new CreditCardsAdapers(getContext().getApplicationContext(), R.layout.lv_item_credit_card, creditCards, inflater);
        lvCreditCards.setAdapter(adaper);
        cardService = new CreditCardService(getContext().getApplicationContext());

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext().getApplicationContext(), AddCreditCardActivity.class);
            startActivityForResult(intent,ADD_CREDIT_CARD_REQ );
        });

        cardService.getAllUserCards(getUserCardsCallback());
        return view;

    }

    @NotNull
    private Callback<List<CreditCard>> getUserCardsCallback() {
        return new Callback<List<CreditCard>>() {
            @Override
            public void runResultOnUiThread(List<CreditCard> result) {
                if(result != null){
                    creditCards.clear();
                    creditCards.addAll(result);
                    ArrayAdapter adapter = (ArrayAdapter) lvCreditCards.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CREDIT_CARD_REQ && resultCode == AddCreditCardActivity.RESULT_OK){
           if(data !=null){
               CreditCard card = (CreditCard) data.getSerializableExtra(AddCreditCardActivity.CREDIT_CARD_KEY);
               cardService.insertCard(card, insertCreditCardCallback());

           }
        }
    }

    @NotNull
    private Callback<CreditCard> insertCreditCardCallback() {
        return new Callback<CreditCard>() {
            @Override
            public void runResultOnUiThread(CreditCard result) {
                creditCards.add(result);
                ArrayAdapter adapter =(ArrayAdapter) lvCreditCards.getAdapter();
                adapter.notifyDataSetChanged();
            }
        };
    }
}