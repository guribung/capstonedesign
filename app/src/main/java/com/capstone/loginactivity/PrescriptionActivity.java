package com.capstone.loginactivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity{

    private EditText pName, pAnum, pCode;
    private Button addDrug, editEnd;
    private ListView listView;
    private ArrayList<ListItem> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        EditAdapter adapter = new EditAdapter();
        pName = findViewById(R.id.name_edit);
        pAnum = findViewById(R.id.regnum_edit);
        pCode = findViewById(R.id.dis_code);
        addDrug = findViewById(R.id.add_drug);
        editEnd = findViewById(R.id.end_button);
        listView = findViewById(R.id.PlistView);
        adapter.addItem();
        listView.setAdapter(adapter);

        addDrug.setOnClickListener(v -> {
            adapter.addItem();
            adapter.notifyDataSetChanged();
            listView.setSelection(adapter.getCount() - 1);
        });



        editEnd.setOnClickListener(v -> {
            String name = pName.getText().toString().trim();
            String admNum = pAnum.getText().toString().trim();
            String code = pCode.getText().toString().trim();
            ArrayList<String> medicineList = new ArrayList<>();
            for (int i=0; i<=adapter.getCount();i++){
                listItems.add((ListItem) adapter.getItem(i));
                String medicine = listItems.get(i).getMedicine();
                String dayDosage = listItems.get(i).getDayDosage();
                String timeDosage = listItems.get(i).getTimeDosage();
                String freq = listItems.get(i).getFreq();
                String sum = listItems.get(i).getSum();
                medicineList.add(medicine+"_"+dayDosage+"_"+timeDosage+"_"+freq+"_"+sum);
            }

        });


    }

}