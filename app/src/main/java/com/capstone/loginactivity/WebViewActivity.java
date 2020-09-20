package com.capstone.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    Button endButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent getIntent = getIntent();
        String roomId = getIntent.getStringExtra("contact");
        webView = findViewById(R.id.web_view);
        webView.loadUrl("appr.tc/r/"+roomId);
        endButton = findViewById(R.id.end_button);

        endButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebViewActivity.this, CallEndActivity.class);
            intent.putExtra("contact",roomId);
            finish();
        });
    }
}