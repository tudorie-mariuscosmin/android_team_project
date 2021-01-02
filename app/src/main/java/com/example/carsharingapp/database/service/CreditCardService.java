package com.example.carsharingapp.database.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carsharingapp.LoginActivity;
import com.example.carsharingapp.asyncTask.AsyncTaskRunner;
import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.DatabaseManager;
import com.example.carsharingapp.database.dao.CreditCardDao;
import com.example.carsharingapp.database.models.CreditCard;

import java.util.List;
import java.util.concurrent.Callable;

public class CreditCardService {
    private CreditCardDao cardDao;
    private AsyncTaskRunner asyncTaskRunner;
    private SharedPreferences sharedPref;

    public CreditCardService(Context context){
        cardDao = DatabaseManager.getInstance(context).getCreditCardDao();
        asyncTaskRunner = new AsyncTaskRunner();
        sharedPref = context.getSharedPreferences(LoginActivity.LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void insertCard(CreditCard card, Callback<CreditCard> callback){
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                if(card == null){
                    return null;
                }
                long cardId = cardDao.insertCard(card);
                if(cardId == -1){
                    return null;
                }
                card.setId(cardId);
                return card;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void getAllUserCards(Callback<List<CreditCard>> callback){
        long userId = sharedPref.getLong(LoginActivity.USER_ID, -1);
        Callable <List<CreditCard>> callable = new Callable<List<CreditCard>>() {
            @Override
            public List<CreditCard> call() throws Exception {
               if(userId == -1){
                   return null;
               }
               return cardDao.findAll(userId);

            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public  void deleteCard(CreditCard card,Callback<Integer> callback ){
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                if(card == null){
                    return -1;
                }
                return  cardDao.delete(card);
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(CreditCard card, Callback<CreditCard> callback){
        Callable<CreditCard> callable = new Callable<CreditCard>() {
            @Override
            public CreditCard call() throws Exception {
                if(card == null){
                    return null;
                }
                int count = cardDao.update(card);
                if(count == -1){
                    return null;
                }
                return card;
            }
        };
        asyncTaskRunner.executeAsync(callable,callback);
    }
}
