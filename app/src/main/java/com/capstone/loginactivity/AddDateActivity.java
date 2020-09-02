package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddDateActivity extends AppCompatActivity {
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_date);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = "";
                if (month<10) {
                    if (dayOfMonth<10){
                        date = year+" 0"+(month+1)+" 0"+dayOfMonth;
                    }
                    else{
                        date = year+" 0"+(month+1)+" "+dayOfMonth;
                    }
                }
                else {
                    if(dayOfMonth<10){
                        date = year+" "+(month+1)+" 0"+dayOfMonth;
                    }
                    else{
                        date = year+" "+(month+1)+" "+dayOfMonth;
                    }
                }

                Intent intent = new Intent(AddDateActivity.this,AddTimeActivity.class);
                if (date != null)
                    intent.putExtra("date",date);
                finish();
                startActivity(intent);
            }
        });

    }
}