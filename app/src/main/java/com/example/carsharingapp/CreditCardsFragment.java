package com.example.carsharingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    public static final int UPDATE_CARD_REQ = 333;
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
        View view =  inflater.inflate(R.layout.fragment_credit_cards, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab_cred_card_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext().getApplicationContext(), AddCreditCardActivity.class);
            startActivityForResult(intent,ADD_CREDIT_CARD_REQ );
        });


        creditCards = new ArrayList<>();
        lvCreditCards = view.findViewById(R.id.lv_cred_cards);
        CreditCardsAdapers adaper = new CreditCardsAdapers(getContext().getApplicationContext(), R.layout.lv_item_credit_card, creditCards, inflater);
        //ArrayAdapter adaper = new ArrayAdapter(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, creditCards);
        lvCreditCards.setAdapter(adaper);
        registerForContextMenu(lvCreditCards);




        cardService = new CreditCardService(getContext().getApplicationContext());
        cardService.getAllUserCards(getUserCardsCallback());


        return view;

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater =getActivity().getMenuInflater();
        inflater.inflate(R.menu.card_context, menu);


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        CreditCard card = (CreditCard) lvCreditCards.getAdapter().getItem(info.position);
        if(item.getItemId() == R.id.menu_card_edit){
            Intent intent = new Intent(getContext().getApplicationContext(), AddCreditCardActivity.class);
            intent.putExtra(AddCreditCardActivity.CREDIT_CARD_KEY, card);
            startActivityForResult(intent, UPDATE_CARD_REQ);
            return true;
        }else{
            cardService.deleteCard(card, deleteCardCallback(card));
            return true;
        }
    }

    @NotNull
    private Callback<Integer> deleteCardCallback(CreditCard card) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if(result != -1){
                    creditCards.remove(card);
                    ArrayAdapter adapter =(ArrayAdapter) lvCreditCards.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        };
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
        if(data != null && resultCode == AddCreditCardActivity.RESULT_OK){
            CreditCard card = (CreditCard) data.getSerializableExtra(AddCreditCardActivity.CREDIT_CARD_KEY);
            if(requestCode == ADD_CREDIT_CARD_REQ){
                cardService.insertCard(card, insertCreditCardCallback());
            }
            if(requestCode == UPDATE_CARD_REQ){
                cardService.update(card, updateCardCallback());
            }
        }
    }

    @NotNull
    private Callback<CreditCard> updateCardCallback() {
        return new Callback<CreditCard>() {
            @Override
            public void runResultOnUiThread(CreditCard result) {
                if(result != null){
                    for(CreditCard card : creditCards){
                        if(card.getId() == result.getId()){
                            card.setCardholderName(result.getCardholderName());
                            card.setCardNumber(result.getCardNumber());
                            card.setExpiration(result.getExpiration());
                            card.setCvv(result.getCvv());
                            card.setType(result.getType());
                            break;
                        }
                    }
                    ArrayAdapter adapter =(ArrayAdapter) lvCreditCards.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        };
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