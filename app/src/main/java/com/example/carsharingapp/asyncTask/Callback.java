package com.example.carsharingapp.asyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
