package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PharmacyPrescriptionViewActivity extends AppCompatActivity {
    private TextView name,adrnum,code;
    private ListView listView;
    private Button addbtn;
    private ArrayList<ListItem> listItems = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_prescription_view);

        String mName = getIntent().getStringExtra("name");
        String admNum = getIntent().getStringExtra("adNum");
        String mCode = getIntent().getStringExtra("code");
        String medicineList = getIntent().getStringExtra("medicine");
        MedicineAdapter adapter = new MedicineAdapter();
        name = findViewById(R.id.name_phar);
        name.setText(mName);
        adrnum = findViewById(R.id.reg_num_phar);
        adrnum.setText(admNum);
        code = findViewById(R.id.dis_code_phar);
        code.setText(mCode);
        listView = findViewById(R.id.VlistView);
        addbtn = findViewById(R.id.end_button_phar);

        listView.setAdapter(adapter);
        String[] medicinendose = medicineList.split("%");
        for (int i = 0; medicinendose.length-1>= i ; i++){
            adapter.addItem(medicinendose[i]);
            adapter.notifyDataSetChanged();
        }

        addbtn.setOnClickListener(v -> {
            Intent intent = new Intent(PharmacyPrescriptionViewActivity.this,DeliveryInfoActivity.class);
            startActivity(intent);
            finish();
        });
    }
}