package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PrescriptionActivity extends AppCompatActivity{

    private EditText pName, pAnum, pCode;
    private Button addDrug, editEnd;
    private ListView listView;
    private ArrayList<ListItem> listItems = new ArrayList<>();
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        EditAdapter adapter = new EditAdapter();
        Intent getIntent = getIntent();
        String patientUid = getIntent.getStringExtra("patientUid");
        String clinicName = getIntent.getStringExtra("clinicName");
        String datetime = getIntent.getStringExtra("datetime");
        pName = findViewById(R.id.name_edit);
        pAnum = findViewById(R.id.regnum_edit);
        pCode = findViewById(R.id.dis_code);
        addDrug = findViewById(R.id.add_drug);
        addDrug.setVisibility(View.INVISIBLE);
        editEnd = findViewById(R.id.end_button);
        listView = findViewById(R.id.PlistView);
        firebaseAuth =  FirebaseAuth.getInstance();
        adapter.addItem();
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
            for (int i = 0; i < adapter.getCount(); i++) {
                View view = listView.getChildAt(i);
                EditText medicineEdit = view.findViewById(R.id.medicine_name);
                String medicine = medicineEdit.getText().toString();
                EditText editText1 = view.findViewById(R.id.fet1);
                String dayDosage = editText1.getText().toString();
                EditText editText2 = view.findViewById(R.id.fet2);
                String timeDosage = editText2.getText().toString();
                EditText editText3 = view.findViewById(R.id.fet3);
                String freq = editText3.getText().toString();
                EditText editText4 = view.findViewById(R.id.fet4);
                String sum = editText4.getText().toString();
                medicineList.add(medicine + "_" + dayDosage + "_" + timeDosage + "_" + freq + "_" + sum);
            }
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference ref = database.child("Prescription");
            HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("admNum", admNum);
            hashMap.put("code", code);
            hashMap.put("clinicName", clinicName);
            hashMap.put("doctorUid", firebaseAuth.getUid());
            hashMap.put("patientUid", patientUid);
            hashMap.put("preId",firebaseAuth.getUid() + "_" + datetime);
            String medi = "";
            for (String medicine : medicineList) {
                medi = medi + medicine + "%";
            }
            hashMap.put("medicine", medi);
            ref.child(firebaseAuth.getUid() + "_" + datetime).setValue(hashMap);

            Intent intent = new Intent(PrescriptionActivity.this, SetPharmacyActivity.class);
            intent.putExtra("prescriptionID", firebaseAuth.getUid() + "_" + datetime);
            intent.putExtra("datetime", datetime);
            PrescriptionActivity.this.startActivity(intent);
            PrescriptionActivity.this.finish();
        });


    }

}