package com.capstone.loginactivity;

import android.os.Bundle;
import android.util.Log;
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

public class InfoActivity extends AppCompatActivity {
    private TextView tv_id, tv_pass, tv_name, viewEmail, viewMember, viewName;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "infoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_info);


        tv_id = findViewById(R.id.tv_id);
        tv_pass = findViewById(R.id.tv_pass);
        tv_name = findViewById(R.id.tv_name);
        viewEmail = findViewById(R.id.viewEmail);
        viewMember = findViewById(R.id.viewMember);
        viewName = findViewById(R.id.viewName);

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Users");
        Query userQuery = ref.orderByChild("uid").equalTo(firebaseAuth.getUid());

        final User userdata = new User();

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    userdata.setEmail(String.valueOf(postSnapshot.child("email").getValue(String.class)));
                    userdata.setMember(String.valueOf(postSnapshot.child("member").getValue(String.class)));
                    userdata.setName(String.valueOf(postSnapshot.child("name").getValue(String.class)));
                    userdata.setUid(String.valueOf(postSnapshot.child("uid").getValue(String.class)));
                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url

                    if(userdata.member.equals("의사")){
                        viewName.setText("병원이름");
                    }

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getIdToken() instead.
                    String uid = user.getUid();
                    tv_id.setText(userdata.email);
                    tv_pass.setText(userdata.member);
                    tv_name.setText(userdata.name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });




    }
}