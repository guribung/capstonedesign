package com.capstone.loginactivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class UserItemView extends LinearLayout {
    TextView tName, tHp;

    public UserItemView(Context context) {
        super(context);
        init(context);
    }
    public UserItemView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.user_item_list,this,true);

        tName= findViewById(R.id.name);
        tHp = findViewById(R.id.HP);
    }
    public void settName(String name){
        tName.setText(name);
    }
    public void setHP(String HP){
        tHp.setText(HP);
    }
}
