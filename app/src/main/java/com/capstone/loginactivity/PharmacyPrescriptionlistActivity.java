package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PharmacyPrescriptionlistActivity extends AppCompatActivity {
    private ListView preListView;
    private ArrayAdapter adapter;
    private ArrayList<String> listitem;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_prescriptionlist);
        ArrayList<String> dateTime = new ArrayList<>();
        ArrayList<String> docUid = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> adNumList = new ArrayList<>();
        ArrayList<String> codeList = new ArrayList<>();
        ArrayList<String> medicineList = new ArrayList<>();
        preListView = findViewById(R.id.pre_listview);
        preListView.setEmptyView(findViewById(android.R.id.empty));
        preListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PharmacyPrescriptionlistActivity.this, adNumList.get(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PharmacyPrescriptionlistActivity.this, PharmacyPrescriptionViewActivity.class);
                intent.putExtra("name",nameList.get(i));
                intent.putExtra("adNum",adNumList.get(i));
                intent.putExtra("code",codeList.get(i));
                intent.putExtra("medicine",medicineList.get(i));
                startActivity(intent);
                finish();
            }
        });
        registerForContextMenu(preListView);


        listitem = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listitem);
        preListView.setAdapter(adapter);
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Prescription");
        Query pharmacistQuery = ref.orderByChild("pharmacistUid").equalTo(firebaseAuth.getUid());
        pharmacistQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // TODO: handle the post
                    docUid.add(postSnapshot.child("doctorUid").getValue(String.class));
                    dateTime.add(postSnapshot.child("datetime").getValue(String.class));
                    nameList.add(postSnapshot.child("name").getValue(String.class));
                    adNumList.add(postSnapshot.child("admNum").getValue(String.class));
                    codeList.add(postSnapshot.child("code").getValue(String.class));
                    medicineList.add(postSnapshot.child("medicine").getValue(String.class));
                    String[] reservation = postSnapshot.child("datetime").getValue(String.class).split(" ");
                    String name = postSnapshot.child("clinicName").getValue(String.class);
                    adapter.add(reservation[0] + "년 " + reservation[1] + "월 " + reservation[2] + "일 " + reservation[3] + "시 " + reservation[4] + "분 " + name);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}