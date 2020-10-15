package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CallEndActivity extends AppCompatActivity {

    private Button reConnect, callEnd, addPrescription;
    private FirebaseAuth firebaseAuth;
    private final ConnectInfo connectInfo = new ConnectInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_end);
        reConnect = findViewById(R.id.recall);
        callEnd = findViewById(R.id.callEnd);
        addPrescription = findViewById(R.id.addPrescription);
        Intent getIntent = getIntent();
        firebaseAuth = FirebaseAuth.getInstance();
        String contactId = getIntent.getStringExtra("contact");
        String[] contactArray = contactId.split("_");
        String datetime = contactArray[0].replace(" ","");
        String doctorUid = contactArray[1];

        reConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CallEndActivity.this, CalllistActivity.class);
                startActivity(intent);
                finish();
            }
        });


        callEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("Reservation");
                Query doctorQuery = ref.orderByChild("doctorUid").equalTo(firebaseAuth.getUid());

                doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            // TODO: handle the post
                            connectInfo.setDatetime(postSnapshot.child("datetime").getValue(String.class).replace(" ",""));
                            connectInfo.setDocUid(postSnapshot.child("doctorUid").getValue(String.class));
                            if (doctorUid.equals(connectInfo.getDocUid()) && datetime.equals(connectInfo.getDatetime()))
                                postSnapshot.getRef().removeValue();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });

        addPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("Reservation");
                Query doctorQuery = ref.orderByChild("doctorUid").equalTo(firebaseAuth.getUid());

                doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            // TODO: handle the post
                            connectInfo.setIntime(postSnapshot.child("datetime").getValue(String.class));
                            connectInfo.setDatetime(postSnapshot.child("datetime").getValue(String.class).replace(" ",""));
                            connectInfo.setDocUid(postSnapshot.child("doctorUid").getValue(String.class));
                            connectInfo.setPatientUid(postSnapshot.child("patientUid").getValue(String.class));
                            connectInfo.setClinicName(postSnapshot.child("clinicName").getValue(String.class));
                            if (doctorUid.equals(connectInfo.docUid) && datetime.equals(connectInfo.datetime)){
                                postSnapshot.getRef().removeValue();
                                Intent intent = new Intent(CallEndActivity.this, PrescriptionActivity.class);
                                intent.putExtra("patientUid",connectInfo.getPatientUid());
                                intent.putExtra("clinicName",connectInfo.getClinicName());
                                intent.putExtra("datetime",connectInfo.getIntime());
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}