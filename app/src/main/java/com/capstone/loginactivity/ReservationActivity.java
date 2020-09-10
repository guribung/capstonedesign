package com.capstone.loginactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

public class ReservationActivity extends AppCompatActivity {
    private static final String TAG = "ReservationActivity";
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> listitem;
    private FirebaseAuth firebaseAuth;
    private TextView textA,empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        listView = findViewById(R.id.room_listview);
        empty = findViewById(R.id.empty);
        textA = findViewById(R.id.textA);
        listitem = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listitem);
        listView.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Reservation");
        Query doctorQuery = ref.orderByChild("doctorUid").equalTo(firebaseAuth.getUid());

        doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    String[] reservation = postSnapshot.child("datetime").getValue(String.class).split(" ");
                    String name =  postSnapshot.child("name").getValue(String.class);
                    adapter.add(reservation[0]+"년 "+reservation[1]+"월 "+reservation[2]+"일 "+reservation[3]+"시 "+reservation[4]+"분 "+name);
                    Toast.makeText(ReservationActivity.this, "ssucess "+ name,Toast.LENGTH_SHORT).show();
                }
                empty.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query userQuery = ref.orderByChild("patientUid").equalTo(firebaseAuth.getUid());

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    String[] reservation = postSnapshot.child("datetime").getValue(String.class).split(" ");
                    String clinicName =  postSnapshot.child("clinicName").getValue(String.class);
                    adapter.add(reservation[0]+"년 "+reservation[1]+"월 "+reservation[2]+"일 "+reservation[3]+"시 "+reservation[4]+"분 "+clinicName);
                    Toast.makeText(ReservationActivity.this, "ssucess "+ clinicName,Toast.LENGTH_SHORT).show();
                }
                empty.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}