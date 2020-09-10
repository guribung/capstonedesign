package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class AddTimeActivity extends AppCompatActivity {
    private TimePicker mTimePicker;
    private Button button;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);
        Intent getintent = getIntent();
        mTimePicker = findViewById(R.id.timepicker);
        button = findViewById(R.id.button);
        text = findViewById(R.id.textin);
        String date;

        date = getintent.getStringExtra("date");
        final String mDate = date;


        if (mTimePicker.getHour()<10) {
            if (+mTimePicker.getMinute()<10){
                text.setText(mDate+" 0"+mTimePicker.getHour()+" 0"+mTimePicker.getMinute());
            }
            else{
                text.setText(mDate+" 0"+mTimePicker.getHour()+" "+mTimePicker.getMinute());
            }
        }
        else {
            if(+mTimePicker.getMinute()<10){
                text.setText(mDate+" "+mTimePicker.getHour()+" 0"+mTimePicker.getMinute());
            }
            else{
                text.setText(mDate+" "+mTimePicker.getHour()+" "+mTimePicker.getMinute());
            }
        }
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hour, int min) {
                if (hour<10) {
                    if (min<10){
                        text.setText(mDate+" 0"+hour+" 0"+min);
                    }
                    else{
                        text.setText(mDate+" 0"+hour+" "+min);
                    }
                }
                else {
                    if(min<10){
                        text.setText(mDate+" "+hour+" 0"+min);
                    }
                    else{
                        text.setText(mDate+" "+hour+" "+min);
                    }
                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String datetime = (String) text.getText();
                 Intent intent = new Intent(AddTimeActivity.this, AddReservationActivity.class);
                 intent.putExtra("datetime",datetime);
                 startActivity(intent);
                 finish();
             }
         });

    }
}