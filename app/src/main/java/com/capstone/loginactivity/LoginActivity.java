package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Button mRegBtn,mLoginBtn;
    private EditText mEmailText, mPasswordText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        firebaseAuth =  FirebaseAuth.getInstance();
        //버튼 등록하기
        mRegBtn = findViewById(R.id.btn_register);
        mLoginBtn = findViewById(R.id.btn_login);
        mEmailText = findViewById(R.id.et_id);
        mPasswordText = findViewById(R.id.et_pass);


        //가입 버튼이 눌리면
        mRegBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });

        //로그인 버튼이 눌리면
        mLoginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email = mEmailText.getText().toString().trim();
                boolean email_check = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?",email);
                String pwd = mPasswordText.getText().toString().trim();
                if (email_check) {
                    firebaseAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, ManuActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                    Toast.makeText(LoginActivity.this, "이메일을 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }


}