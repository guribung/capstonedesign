package com.capstone.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class EditInfoActivity extends AppCompatActivity {
    private static final String TAG = "EditInfoActivity";
    private EditText editEmail, editPass, chkPass, editName, editPhone;
    private Button setEmail, setPass, setName, setPhone, setUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        editEmail = findViewById(R.id.edit_email);
        editPass = findViewById(R.id.edit_password);
        chkPass = findViewById(R.id.chk_pass);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);

        setEmail = findViewById(R.id.email_btn);
        setPass = findViewById(R.id.pass_btn);
        setName = findViewById(R.id.name_btn);
        setPhone = findViewById(R.id.phone_btn);
        setUp = findViewById(R.id.btn_setup);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Users");

        //이메일 변경
        setEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editEmail.getText());
                boolean email_cheak =  Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?",email);
                if (email_cheak) {
                    user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated");
                            }
                        }
                    });
                } else {
                    editEmail.setHint("이메일을 다시 입력해주세요");
                }
            }
        });
        //비밀번호 변경
        setPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passWord, checkPass;
                passWord = String.valueOf(editPass.getText());
                checkPass = String.valueOf(chkPass.getText());
                if (passWord.contentEquals(checkPass)){
                    user.updatePassword(passWord)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d(TAG,"User password updated");
                                    }
                                }
                            });
                }
                else{
                    editPass.setHint("비밀번호를 다시 입력해주세요");
                    chkPass.setHint("비밀번호를 다시 입력해주세요");
                }
            }
        });
        //이름변경
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(editName.getText());
                boolean name_check = Pattern.matches("^[가-힣]*$",name);
                if (name_check){
                    ref.child(firebaseAuth.getUid()).child("name").setValue(name);
                    Log.d(TAG,"User name updated");
                }
                else{
                    editName.setHint("이름을 다시 입력해주세요");
                }
            }
        });
        //전화번호 변경
        setPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = String.valueOf(editPhone.getText());
                boolean phone_check = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",phone);
                if (phone_check){
                    ref.child(firebaseAuth.getUid()).child("phone").setValue(phone);
                    Log.d(TAG,"User phone updated");
                }
                else{
                    editPhone.setHint("전화번호를 다시 입력해주세요");
                }
            }
        });

        //액티비티를 종료하고 이전으로 돌아감
        setUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditInfoActivity.this, InfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}