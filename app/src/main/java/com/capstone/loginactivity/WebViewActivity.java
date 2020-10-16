package com.capstone.loginactivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSet;
    private Button endButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        firebaseAuth = FirebaseAuth.getInstance();
        Intent getIntent = getIntent();
        String roomId = getIntent.getStringExtra("contact");
        MediaProjectionManager mpm = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        //startActivityForResult(mpm.createScreenCaptureIntent(),);
        webView = findViewById(R.id.web_view);
        webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);
        webSet.setLoadsImagesAutomatically(true);
        webSet.setDomStorageEnabled(true);
        webSet.setAllowContentAccess(true);
        webSet.setAppCacheEnabled(true);
        webSet.setMediaPlaybackRequiresUserGesture(false);
        webSet.setAllowContentAccess(true);
        webSet.setAllowFileAccessFromFileURLs(true);
        webSet.setAllowUniversalAccessFromFileURLs(true);
        webSet.setUserAgentString(webView.getSettings().getUserAgentString() + " Android_Mobile");
        endButton = findViewById(R.id.end_button);
        webView.setWebChromeClient(new WebChromeClient(){
            @TargetApi(Build.VERSION_CODES.Q)
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    request.grant(request.getResources());
                }
            }
        });


        webView.loadUrl("https://appr.tc/r/"+roomId);
        endButton.setOnClickListener(v -> {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference ref = database.child("Users");
            Query userQuery = ref.orderByChild("uid").equalTo(firebaseAuth.getUid());
            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()){
                        if (postSnapshot.child("member").getValue(String.class).equals("환자")){
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Intent intent = new Intent(WebViewActivity.this, CallEndActivity.class);
            intent.putExtra("contact",roomId);
            startActivity(intent);
            finish();
        });
    }
}