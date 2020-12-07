package com.example.proiect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RegisterAdapter extends ArrayAdapter <RegisterItem>{
    public RegisterAdapter(Context context, ArrayList<RegisterItem> registerList){
        super(context,0,registerList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initViewReg(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initViewReg(position, convertView, parent);
    }

    private View initViewReg(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.register_spinner_row,
                    parent,false);
        }

        ImageView imageViewRegister=convertView.findViewById(R.id.image_view_flag_reg);
        TextView textViewNameRegister=convertView.findViewById(R.id.text_view_name_reg);

        RegisterItem currentItemReg=getItem(position);

        if(currentItemReg != null)  {
            imageViewRegister.setImageResource(currentItemReg.getmShareRegImage());
            textViewNameRegister.setText(currentItemReg.getmRegisterName());
        }
        return convertView;
    }
}
