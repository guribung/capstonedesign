package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class CalllistActivity extends AppCompatActivity {
    private ListView roomListView;
    private ArrayAdapter adapter;
    private ArrayList<String> listitem;
    private FirebaseAuth firebaseAuth;
    private TextView textA,empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calllist);
        ArrayList<String> dateTime = new ArrayList<>();
        ArrayList<String> docUid = new ArrayList<>();
        roomListView = findViewById(R.id.call_listview);
        roomListView.setEmptyView(findViewById(android.R.id.empty));
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String roomId = dateTime.get(i) + "_" + docUid.get(i);
              /*String roomId = ((TextView) view).getText().toString().replace(" ","")
                      .replace("년","")
                      .replace("월","")
                      .replace("일","")
                      .replace("시","")x
                      .replace("분","");*/
                Intent intent = new Intent(CalllistActivity.this, WebViewActivity.class);
                intent.putExtra("contact",roomId);
                startActivity(intent);
                finish();
            }
        });
        registerForContextMenu(roomListView);


        listitem = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listitem);
        roomListView.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Reservation");
        Query doctorQuery = ref.orderByChild("doctorUid").equalTo(firebaseAuth.getUid());

        doctorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // TODO: handle the post
                    int i = 0;
                    dateTime.add(postSnapshot.child("datetime").getValue(String.class).replace(" ", ""));
                    docUid.add(postSnapshot.child("doctorUid").getValue(String.class));
                    String[] reservation = postSnapshot.child("datetime").getValue(String.class).split(" ");
                    String name = postSnapshot.child("name").getValue(String.class);
                    adapter.add(reservation[0] + "년 " + reservation[1] + "월 " + reservation[2] + "일 " + reservation[3] + "시 " + reservation[4] + "분 " + name);
                    i++;
                }
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

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    // TODO: handle the post
                    int i = 0;
                    dateTime.add(postSnapshot.child("datetime").getValue(String.class).replace(" ", ""));
                    docUid.add(postSnapshot.child("doctorUid").getValue(String.class));
                    String[] reservation = postSnapshot.child("datetime").getValue(String.class).split(" ");
                    String clinicName = postSnapshot.child("clinicName").getValue(String.class);
                    adapter.add(reservation[0] + "년 " + reservation[1] + "월 " + reservation[2] + "일 " + reservation[3] + "시 " + reservation[4] + "분 " + clinicName);
                    i++;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}