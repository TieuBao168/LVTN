package com.example.logisticaliot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticaliot.GetDataVolley.GetInformation;

public class ControlActivity extends AppCompatActivity {
    WebView webView;
    Button MapBtn,ReloadBtn,DataBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        webView = findViewById(R.id.webview);
        MapBtn = findViewById(R.id.Map_Btn);
        ReloadBtn = findViewById(R.id.Reload_Btn);
        DataBtn = findViewById(R.id.DataBtn);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.com/");

        ReloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("https://www.google.com/");
            }
        });

        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ControlActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        DataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ControlActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
}
