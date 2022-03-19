package com.example.logisticaliot;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticaliot.GetDataVolley.GetHumidity;
import com.example.logisticaliot.GetDataVolley.GetInformation;
import com.example.logisticaliot.GetDataVolley.GetTemperature;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControlActivity extends AppCompatActivity {
    Button MapBtn,ReloadBtn, DataBtn, OnOffBtn, OkBtn;
    TextView ThongtinView, NhietdoView, DoamView;
    EditText InputValue;
    WebView webView;
    ImageView AirConditionerImg;
    String Handle, Auto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);


        ThongtinView = findViewById(R.id.ThongtinView);
        MapBtn = findViewById(R.id.Map_Btn);
        ReloadBtn = findViewById(R.id.Reload_Btn);
//        webView = findViewById(R.id.webview);
        DataBtn = findViewById(R.id.Data_Btn);
        OnOffBtn = findViewById(R.id.OnOff_Btn);
        OkBtn = findViewById(R.id.Ok_Btn);
        InputValue = findViewById(R.id.InputValue);
        AirConditionerImg = findViewById(R.id.AirConditioner_Img);


//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(Urls.WEBSERVER_URL);
//
//        ReloadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webView.setWebViewClient(new WebViewClient());
//                webView.getSettings().setJavaScriptEnabled(true);
//                webView.loadUrl(Urls.WEBSERVER_URL);
//            }
//        });
        OnOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnOffBtn.getText()=="ON") {
                    OnOffBtn.setText("OFF");
                    OnOffBtn.setBackgroundColor(Color.RED);
                    Handle = "On";
                    AirConditionerImg.setImageResource(R.drawable.airconditioneropen);
                } else{
                    OnOffBtn.setText("ON");
                    OnOffBtn.setBackgroundColor(Color.BLUE);
                    Handle = "Off";
                    AirConditionerImg.setImageResource(R.drawable.airconditionerclose);
                }
            }
        });

        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InputValue.getText().toString()==null){
                    Toast.makeText(ControlActivity.this, "Chưa nhập giá trị", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ControlActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ControlActivity.this, MapActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        DataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ControlActivity.this, DataActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

//    @Override
//    public void onBackPressed(){
//        if(webView.canGoBack()){
//            webView.goBack();
//        }
//        else{
//            super.onBackPressed();
//        }
//    }
}