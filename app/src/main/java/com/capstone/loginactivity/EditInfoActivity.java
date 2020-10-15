package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import static com.capstone.loginactivity.R.id.pass_btn;

public class EditInfoActivity extends AppCompatActivity {
    private static final String TAG = "EditInfoActivity";
    private EditText editEmail, editName, editPhone;
    private Button setEmail, setPass, setName, setPhone, setUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        editEmail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);

        setEmail = findViewById(R.id.email_btn);
        setPass = findViewById(pass_btn);
        setName = findViewById(R.id.name_btn);
        setPhone = findViewById(R.id.phone_btn);
        setUp = findViewById(R.id.btn_setup);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Users");

        //이메일 변경
        setEmail.setOnClickListener(v -> {
            String email = String.valueOf(editEmail.getText());
            boolean email_cheak =  Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                    "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                    "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                    "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",email);
            if (email_cheak) {
                FirebaseAuth.getInstance().getCurrentUser().updateEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditInfoActivity.this, "이메일이 변경되었습니다", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "User email address updated");
                    }
                });
            } else {
                Toast.makeText(EditInfoActivity.this, "이메일을 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
        //비밀번호 변경
        setPass.setOnClickListener(v ->
                FirebaseAuth.getInstance().sendPasswordResetEmail(user.getEmail()).
                        addOnCompleteListener(task ->
                                Toast.makeText(EditInfoActivity.this, "비밀번호 재설정 이메일이 전송되었습니다", Toast.LENGTH_SHORT).show()));
        //이름변경
        setName.setOnClickListener(v -> {
            String name = String.valueOf(editName.getText());
            boolean name_check = Pattern.matches("^[가-힣]*$",name);
            if (name_check&&!name.equals("")){
                ref.child(firebaseAuth.getUid()).child("name").setValue(name);
                Toast.makeText(EditInfoActivity.this, "이름이 변경되었습니다", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"User name updated");
            }
            else{
                Toast.makeText(EditInfoActivity.this, "이름을 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
        //전화번호 변경
        setPhone.setOnClickListener(v -> {
            String phone = String.valueOf(editPhone.getText());
            boolean phone_check = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",phone);
            if (phone_check&&!phone.equals("")){
                ref.child(firebaseAuth.getUid()).child("phone").setValue(phone);
                Toast.makeText(EditInfoActivity.this, "전화번호가 변경되었습니다", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"User phone updated");
            }
            else{
                Toast.makeText(EditInfoActivity.this, "전화번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        //액티비티를 종료하고 이전으로 돌아감
        setUp.setOnClickListener(v -> {
            Intent intent = new Intent(EditInfoActivity.this, InfoActivity.class);
            startActivity(intent);
            finish();
        });
    }
}