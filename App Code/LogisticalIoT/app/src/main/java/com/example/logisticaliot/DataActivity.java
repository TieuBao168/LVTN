package com.example.logisticaliot;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticaliot.GetDataVolley.GetHumidity;
import com.example.logisticaliot.GetDataVolley.GetInformation;
import com.example.logisticaliot.GetDataVolley.GetLightIntensity;
import com.example.logisticaliot.GetDataVolley.GetTemperature;

public class DataActivity extends AppCompatActivity {
    Button MapBtn, ReloadBtn, ControlBtn, HistoryBtn;
    TextView ThongtinView, NhietdoView, DoamView, AnhsangView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ThongtinView = findViewById(R.id.ThongtinView);
        NhietdoView = findViewById(R.id.NhietdoView);
        DoamView = findViewById(R.id.DoamView);
//        AnhsangView = findViewById(R.id.AnhsangView);
        MapBtn = findViewById(R.id.Map_Btn);
        ReloadBtn = findViewById(R.id.Reload_Btn);
        ControlBtn = findViewById(R.id.Control_Btn);
        HistoryBtn = findViewById(R.id.History_Btn);

        GetInformation f = new GetInformation();
        f.getJSONArray(DataActivity.this,ThongtinView);
        GetTemperature t = new GetTemperature ();
        t.getJSONArray(DataActivity.this,NhietdoView);
        GetHumidity h = new GetHumidity();
        h.getJSONArray(DataActivity.this,DoamView);
//        GetLightIntensity l = new GetLightIntensity();
//        l.getJSONArray(DataActivity.this,AnhsangView);

//        new CountDownTimer(1000000000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                GetInformation f = new GetInformation();
//                f.getJSONArray(DataActivity.this,ThongtinView);
//                GetTemperature t = new GetTemperature ();
//                t.getJSONArray(DataActivity.this,NhietdoView);
//                GetHumidity h = new GetHumidity();
//                h.getJSONArray(DataActivity.this,DoamView);
//            }
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();

        ReloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetInformation f = new GetInformation();
                // 2. lay mang -> chuyen thanh chuoi
                f.getJSONArray(DataActivity.this,ThongtinView);
                GetTemperature t = new GetTemperature ();
                t.getJSONArray(DataActivity.this,NhietdoView);
                GetHumidity h = new GetHumidity();
                h.getJSONArray(DataActivity.this,DoamView);
//                GetLightIntensity l = new GetLightIntensity();
//                l.getJSONArray(DataActivity.this,AnhsangView);
            }
        });

        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DataActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        HistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DataActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        ControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DataActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });
    }
}
