package com.capstone.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    WebSettings webSet;
    Button endButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent getIntent = getIntent();
        String roomId = getIntent.getStringExtra("contact");
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
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                    request.grant(request.getResources());
            }
        });


        webView.loadUrl("https://appr.tc/r/"+roomId);
        endButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebViewActivity.this, CallEndActivity.class);
            intent.putExtra("contact",roomId);
            startActivity(intent);
            finish();
        });
    }
}