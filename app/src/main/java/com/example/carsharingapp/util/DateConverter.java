package com.example.carsharingapp.util;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private SimpleDateFormat format;

    public  DateConverter(){
        format = new SimpleDateFormat("MM/yy", Locale.US);
    }
    @TypeConverter
    public Date fromString(String s){
        try {
             return format.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }
    @TypeConverter
    public String toString(Date date){
        if(date == null){
            return null;
        }else{
            return format.format(date);
        }
    }
}
