package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class ManuActivity extends AppCompatActivity {
    private static final String TAG = "ManuActivity";
    private Button infoBtn, callBtn, resBtn,addresBtn,testBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        infoBtn = findViewById(R.id.info_btn);
        callBtn = findViewById(R.id.call_btn);
        resBtn = findViewById(R.id.res_btn);
        addresBtn = findViewById(R.id.addres_btn);
        testBtn = findViewById(R.id.test_button);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Users");
        Query userQuery = ref.orderByChild("uid").equalTo(firebaseAuth.getUid());
        final User userdata = new User();
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    userdata.setUid(String.valueOf(postSnapshot.child("uid").getValue(String.class)));
                    userdata.setMember(String.valueOf(postSnapshot.child("member").getValue(String.class)));
                    userdata.setName(String.valueOf(postSnapshot.child("name").getValue(String.class)));
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ManuActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String mToken = instanceIdResult.getToken();
                            ref.child(userdata.uid).child("token").setValue(mToken);
                            Log.e("Token",mToken);
                        }
                    });

                }
                if(userdata.member.equals("의사")){
                    addresBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });


        infoBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManuActivity.this, InfoActivity.class);
                startActivity(intent);

            }
        });
        callBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManuActivity.this, ConnectActivity.class);
                startActivity(intent);

            }
        });
        resBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManuActivity.this, ReservationActivity.class);
                startActivity(intent);

            }
        });
        addresBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManuActivity.this, AddDateActivity.class);
                startActivity(intent);

            }
        });

        testBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ManuActivity.this, PrescriptionActivity.class);
            startActivity(intent);
        });
    }
}