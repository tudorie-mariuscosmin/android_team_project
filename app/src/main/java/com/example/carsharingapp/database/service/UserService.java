package com.example.carsharingapp.database.service;

import android.content.Context;

import com.example.carsharingapp.asyncTask.AsyncTaskRunner;
import com.example.carsharingapp.asyncTask.Callback;
import com.example.carsharingapp.database.DatabaseManager;
import com.example.carsharingapp.database.dao.UserDao;
import com.example.carsharingapp.database.models.User;

import java.util.concurrent.Callable;

public class UserService {
    private final UserDao userDao;
    private  final AsyncTaskRunner asyncTaskRunner;


    public UserService(Context context){
        this.userDao = DatabaseManager.getInstance(context).getUserDao();
        this.asyncTaskRunner = new AsyncTaskRunner();
    }

    public void insertUser(Callback<User> callback, User user){
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                if(user == null){
                    return null;
                }
                long id = userDao.insert(user);
                if(id == -1){
                    return null;
                }
                user.setId(id);
                return  user;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }


    public void findUserByEmail(String email, Callback<User> callback){
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                if(email.isEmpty() || email == null){
                    return null;
                }
                User user = userDao.findUserByEmail(email);
                if(user ==null){
                    return null;
                }
                return user;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

}
