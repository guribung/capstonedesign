package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class AddReservationActivity extends AppCompatActivity {
    private static final String TAG = "AddReservationActivity";
    EditText putName;
    Button searchBtn;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> listitem;
    TextView textv, textB,empty;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);

        textv = findViewById(R.id.textv);
        textB = findViewById(R.id.textB);
        putName = findViewById(R.id.put_name);
        searchBtn = findViewById(R.id.search_btn);
        final String datetime;
        Intent getintent = getIntent();
        datetime = getintent.getStringExtra("datetime");
        listitem = new ArrayList<>();
        ArrayList<String> namelist = new ArrayList<>();
        ArrayList<String> phonelist = new ArrayList<>();
        ArrayList<String> uidlist = new ArrayList<>();
        DatabaseReference uDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uRef = uDatabase.child("Users");
        firebaseAuth =  FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Query uQuery = uRef.orderByChild("uid").equalTo(firebaseAuth.getUid());
        uQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    textv.setText(String.valueOf(postSnapshot.child("name").getValue(String.class)));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("Users");
                Query userQuery = ref.orderByChild("name").equalTo(putName.getText().toString());

                final User userdata = new User();

                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            // TODO: handle the post
                            namelist.add(postSnapshot.child("name").getValue(String.class));
                            phonelist.add(postSnapshot.child("phone").getValue(String.class));
                            uidlist.add(postSnapshot.child("uid").getValue(String.class));
                            adapter.add(postSnapshot.child("name").getValue(String.class)+" "+postSnapshot.child("phone").getValue(String.class));
                        }
                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getIdToken() instead.
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });


        listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listitem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = view.findViewById(android.R.id.text1);
                String text = textView.getText().toString();

                String[] array = text.split(" ");
                String name = array[0];
                String phone = array[1];


                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("Reservation");
                HashMap<Object,String> hashMap = new HashMap<>();

                hashMap.put("datetime",datetime);
                hashMap.put("name", namelist.get(position));
                hashMap.put("phone",phonelist.get(position));
                hashMap.put("clinicName",textv.getText().toString());
                hashMap.put("doctorUid",firebaseAuth.getUid());
                hashMap.put("patientUid",uidlist.get(position));
                ref.child(datetime+" "+firebaseAuth.getUid()).setValue(hashMap);

                finish();

            }
        });
    }
}