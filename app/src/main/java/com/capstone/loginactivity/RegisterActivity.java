package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText mEmailText, mPasswordText, mPasswordcheckText, mName, mPhone;
    private Button mregisterBtn;
    private TextView textview;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("회원가입");

        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth =  FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        mEmailText = findViewById(R.id.et_id);
        mPasswordText = findViewById(R.id.et_pass);
        mPasswordcheckText = findViewById(R.id.et_ckpass);
        mregisterBtn = findViewById(R.id.btn_register);
        mName = findViewById(R.id.et_name);
        mPhone = findViewById(R.id.et_phone);
        textview = findViewById(R.id.textView);
        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Member,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1)
                    mName.setHint("병원이름을 입력하세요");

                else if(position == 2)
                    mName.setHint("약국이름을 입력하세요");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //파이어베이스 user 로 접글

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        mregisterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //가입 정보 가져오기
                final String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                String pwdcheck = mPasswordcheckText.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();

                boolean name_check = Pattern.matches("^[가-힣]*$",name);
                boolean phone_check = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",phone);
                final String[] token = new String[1];
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( RegisterActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String mToken = instanceIdResult.getToken();
                        token[0] = mToken;
                        Log.e("Token",mToken);
                    }
                });

                if(pwd.equals(pwdcheck)||name_check||phone_check) {
                    Log.d(TAG, "등록 버튼 " + email + " , " + pwd);


                    //파이어베이스에 신규계정 등록하기
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //가입 성공시
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();
                                String uid = user.getUid();
                                String name = mName.getText().toString().trim();
                                String member = spinner.getSelectedItem().toString();
                                String phone = mPhone.getText().toString().trim();
                                String userTokken = token[0];
                                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                HashMap<Object,String> hashMap = new HashMap<>();

                                hashMap.put("uid",uid);
                                hashMap.put("email",email);
                                hashMap.put("name",name);
                                hashMap.put("member",member);
                                hashMap.put("phone",phone);
                                hashMap.put("token",userTokken);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);


                                //가입이 이루어져을시 가입 화면을 빠져나감.
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                return;  //해당 메소드 진행을 멈추고 빠져나감.

                            }

                        }
                    });

                    //비밀번호 오류시
                }else{

                    Toast.makeText(RegisterActivity.this, "다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
}