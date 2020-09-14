package com.capstone.loginactivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class PrescriptionActivity extends AppCompatActivity {

    private EditText pName, pAnum, pCode;
    private Button addDrug, editEnd, deleteDrug;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        EditAdapter adapter = new EditAdapter();
        pName = findViewById(R.id.name_edit);
        pAnum = findViewById(R.id.regnum_edit);
        pCode = findViewById(R.id.dis_code);
        addDrug = findViewById(R.id.add_drug);
        deleteDrug = findViewById(R.id.delete_drug);
        editEnd = findViewById(R.id.end_button);

        listView = findViewById(R.id.PlistView);
        adapter.addItem();
        listView.setAdapter(adapter);

        addDrug.setOnClickListener(v -> {
            adapter.addItem();
            adapter.notifyDataSetChanged();
            listView.setSelection(adapter.getCount() - 1);
        });

        deleteDrug.setOnClickListener(v -> {

        });

        editEnd.setOnClickListener(v -> {
            String name = pName.getText().toString().trim();
            String admNum = pAnum.getText().toString().trim();
            String code = pCode.getText().toString().trim();
        });
    }


}