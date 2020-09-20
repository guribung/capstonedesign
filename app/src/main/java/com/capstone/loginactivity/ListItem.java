package com.capstone.loginactivity;

import android.content.Context;
import android.text.TextWatcher;

public class ListItem {
    private String medicine;
    private String dayDosage;
    private String timeDosage;
    private String freq;
    private String sum;
    private Context context;
    public TextWatcher textWatcher;
    public String mName, mValue;



    public void setMedicine(String medicine){
        this.medicine = medicine;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public void setDayDosage(String dayDosage){this.dayDosage = dayDosage;}
    public void setTimeDosage(String timeDosage){this.timeDosage = timeDosage;}
    public void setFreq(String freq){
        this.freq = freq;
    }
    public void setSum(String sum){
        this.sum = sum;
    }

    public String getMedicine(){
        return this.medicine;
    }
    public Context getContext(){
        return this.context;
    }
    public String getDayDosage(){
        return this.dayDosage;
    }
    public String getTimeDosage(){
        return this.timeDosage;
    }
    public String getFreq(){
        return this.freq;
    }
    public String getSum(){
        return this.sum;
    }

}
