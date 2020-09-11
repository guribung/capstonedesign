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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
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
    private EditText mEmailText, mPasswordText, mPasswordcheckText, mName, mPhone,mDocNum;
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
        mDocNum = findViewById(R.id.docNum);
        textview = findViewById(R.id.textView);
        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Member,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 ){
                    mName.setHint("이름을 입력하세요");
                    mDocNum.setText("");
                    mDocNum.setVisibility(View.INVISIBLE);
                }
                if(position == 1) {
                    mName.setHint("병원이름을 입력하세요");
                    mDocNum.setHint("의사번호를 입력하세요");
                    mDocNum.setVisibility(View.VISIBLE);
                }
                else if(position == 2) {
                    mName.setHint("약국이름을 입력하세요");
                    mDocNum.setText("");
                    mDocNum.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //파이어베이스 user 로 접글

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        mregisterBtn.setOnClickListener(v -> {

            //가입 정보 가져오기
            final String Bemail = mEmailText.getText().toString().trim();
            String pwd = mPasswordText.getText().toString().trim();
            String pwdcheck = mPasswordcheckText.getText().toString().trim();
            String name = mName.getText().toString().trim();
            String phone = mPhone.getText().toString().trim();
            String docNum = mDocNum.getText().toString().trim();

            boolean email_check = Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",Bemail);
            boolean name_check = Pattern.matches("^[가-힣]*$",name);
            boolean phone_check = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",phone);
            final String[] token = new String[1];
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( RegisterActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String mToken = instanceIdResult.getToken();
                    token[0] = mToken;
                }
            });


            String member = spinner.getSelectedItem().toString();

            String userTokken = token[0];
            boolean input = !Bemail.equals("") && !pwd.equals("") && !pwdcheck.equals("")&&!name.equals("")&&!phone.equals("");
            boolean nullInput = Bemail.equals("") && pwd.equals("") && pwdcheck.equals("")&&name.equals("")&&phone.equals("");
            boolean patternCheck = email_check&&pwd.equals(pwdcheck)&&name_check&&phone_check;
            boolean nullCheck = !email_check&&!pwd.equals(pwdcheck)&&!name_check&&!phone_check;
            if(patternCheck && input && pwd.equals(pwdcheck)) {
                Log.d(TAG, "등록 버튼 " + Bemail + " , " + pwd);


                //파이어베이스에 신규계정 등록하기
                firebaseAuth.createUserWithEmailAndPassword(Bemail, pwd).addOnCompleteListener(RegisterActivity.this, task -> {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String email = user.getEmail();
                    String uid = user.getUid();
                    //가입 성공시
                    if (task.isSuccessful()) {


                        //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                        HashMap<Object,String> hashMap = new HashMap<>();

                        hashMap.put("uid",uid);
                        hashMap.put("email",email);
                        hashMap.put("name",name);
                        hashMap.put("member",member);
                        hashMap.put("phone",phone);
                        hashMap.put("token",userTokken);
                        hashMap.put("doctorLicence",docNum);

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

                });

                //비밀번호 오류시
            }else{
                String text="";
                if (nullInput||nullCheck){
                    Toast.makeText(RegisterActivity.this, text+"다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else

                if (!email_check|| Bemail.equals(""))
                    text = text+"이메일";
                if (!pwd.equals(pwdcheck)||pwd==""|| pwdcheck.equals(""))
                    text= text + " 비밀번호";
                if(!name_check|| name.equals(""))
                    text = text+" 이름";
                if (!phone_check|| phone.equals(""))
                    text = text+" 전화번호";
                Toast.makeText(RegisterActivity.this, text+"을(를) 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }
    public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
}