package com.example.logisticaliot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticaliot.GetDataVolley.GetHumidity;
import com.example.logisticaliot.GetDataVolley.GetInformation;
import com.example.logisticaliot.GetDataVolley.GetTemperature;

public class DisplayActivity extends AppCompatActivity {
    Button MapBtn,ReloadBtn, DataBtn;
    TextView ThongtinView, NhietdoView, DoamView;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ThongtinView = findViewById(R.id.ThongtinView);
        MapBtn = findViewById(R.id.Map_Btn);
        ReloadBtn = findViewById(R.id.Reload_Btn);
        webView = findViewById(R.id.webview);
        DataBtn = findViewById(R.id.Data_Btn);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Urls.WEBSERVER_URL);

        ReloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(Urls.WEBSERVER_URL);
            }
        });

        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DisplayActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        DataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DisplayActivity.this, DataActivity.class);
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
