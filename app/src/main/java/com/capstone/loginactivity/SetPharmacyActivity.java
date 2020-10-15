package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SetPharmacyActivity extends AppCompatActivity {
    private EditText putName;
    private Button searchBtn;
    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> listitem;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "SetPharmacyActivity";
    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAA15ehb_M:APA91bEmbU0N1n4v5gG53ApzdAQOIFgmHFuqzzSbubJEWV6YDtWEuwF1d4oGFhnORwnBsbe1lrGaLP8qKwctnsruvwZ1D6B9w0q2mNynxLJ51EJPOh5GQRKp6tIJHetuvCP2lmWCW53k";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pharmacy);
        putName = findViewById(R.id.phar_put_name);
        searchBtn = findViewById(R.id.phar_search_btn);
        String prescriptionID;
        String datetime;
        Intent getintent = getIntent();
        prescriptionID = getintent.getStringExtra("prescriptionID");
        datetime = getintent.getStringExtra("datetime");
        Toast.makeText(this, datetime, Toast.LENGTH_SHORT).show();
        listitem = new ArrayList<>();
        ArrayList<String> namelist = new ArrayList<>();
        ArrayList<String> phonelist = new ArrayList<>();
        ArrayList<String> uidlist = new ArrayList<>();
        ArrayList<String> tokenlist = new ArrayList<>();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("Users");
                Query userQuery = ref.orderByChild("name").equalTo(putName.getText().toString());
                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            // TODO: handle the post
                            namelist.add(postSnapshot.child("name").getValue(String.class));
                            phonelist.add(postSnapshot.child("phone").getValue(String.class));
                            uidlist.add(postSnapshot.child("uid").getValue(String.class));
                            tokenlist.add(postSnapshot.child("token").getValue(String.class));
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


        listView = findViewById(R.id.pharmacy_list_view);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listitem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = view.findViewById(android.R.id.text1);
                String text = textView.getText().toString();



                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("Prescription");
                ref.child(prescriptionID).child("datetime").setValue(datetime);
                ref.child(prescriptionID).child("pharmacistUid").setValue(uidlist.get(position));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // FMC 메시지 생성 start
                            JSONObject root = new JSONObject();
                            JSONObject notification = new JSONObject();
                            notification.put("body", "처방전이 추가되었습니다");
                            notification.put("title","처방" );
                            root.put("notification", notification);
                            root.put("to", tokenlist.get(position));
                            // FMC 메시지 생성 end

                            URL Url = new URL(FCM_MESSAGE_URL);
                            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
                            conn.setRequestProperty("Accept", "application/json");
                            conn.setRequestProperty("Content-type", "application/json");
                            OutputStream os = conn.getOutputStream();
                            os.write(root.toString().getBytes("utf-8"));
                            os.flush();
                            conn.getResponseCode();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                finish();
            }
        });
    }
}