package com.capstone.loginactivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DeliveryInfoActivity extends AppCompatActivity {
    EditText nameEdit, numEdit;
    Button deliEnd;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilivery_info);

        nameEdit = findViewById(R.id.delivery_name);
        numEdit = findViewById(R.id.delivery_num);
        deliEnd = findViewById(R.id.delivery_end_button);

        firebaseAuth = FirebaseAuth.getInstance();
        String scriptName = getIntent().getStringExtra("scriptName");
        String patientUid = getIntent().getStringExtra("patientUid");

        deliEnd.setOnClickListener(v -> {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference scriptRef = database.child("Prescription");
            Query deliveryQuery = scriptRef.orderByChild("preId").equalTo(scriptName);
            deliveryQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().removeValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            String name = nameEdit.getText().toString();
            String num = numEdit.getText().toString();
            DatabaseReference ref = database.child("Delivery");
            HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("deliveryName", name);
            hashMap.put("deliveryNum", num);
            hashMap.put("deliveryInfo",name+"_"+num);
            hashMap.put("patientUid",patientUid);

            ref.child(name+"_"+num).setValue(hashMap);


            finish();
        });
    }
}