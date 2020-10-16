package com.capstone.loginactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class DeliveryCheckActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter adapter;
    private ArrayList<String> listitem;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_check);
        ArrayList<String> deliveryNameList = new ArrayList<>();
        ArrayList<String> deliveryNumList = new ArrayList<>();
        ArrayList<String> deliveryInfoList = new ArrayList<>();
        listitem = new ArrayList<>();
        listView = findViewById(R.id.delivery_listview);
        listView.setEmptyView(findViewById(android.R.id.empty));
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listitem);
        listView.setAdapter(adapter);
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Delivery");
        Query deliveryQuery = ref.orderByChild("patientUid").equalTo(firebaseAuth.getUid());
        deliveryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot:snapshot.getChildren()){
                    String name = postSnapShot.child("deliveryName").getValue(String.class);
                    String num = postSnapShot.child("deliveryNum").getValue(String.class);
                    String info = postSnapShot.child("deliveryInfo").getValue(String.class);
                    deliveryNameList.add(name);
                    deliveryNumList.add(num);
                    deliveryInfoList.add(info);
                    adapter.add(name +" "+ num);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Query removeQuery = ref.orderByChild("deliveryInfo").equalTo(deliveryInfoList.get(position));
                removeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                        recreate();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return false;
            }
        });
    }
}