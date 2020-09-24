package com.capstone.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

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
        webView.setWebViewClient(new WebViewClient());
        webSet = webView.getSettings();
        webSet.setJavaScriptEnabled(true);
        webSet.setMediaPlaybackRequiresUserGesture(true);
        webSet.setAllowContentAccess(true);
        webView.loadUrl("appr.tc/r/"+roomId);
        endButton = findViewById(R.id.end_button);
        webView.setWebChromeClient(new WebChromeClient(){});

        webView.loadUrl("https://appr.tc/r/"+roomId);
        endButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebViewActivity.this, CallEndActivity.class);
            intent.putExtra("contact",roomId);
            startActivity(intent);
            finish();
        });
    }
}