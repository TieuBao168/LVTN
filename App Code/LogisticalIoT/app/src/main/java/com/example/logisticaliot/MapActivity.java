package com.example.logisticaliot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.logisticaliot.GetDataVolley.GetInformation;
import com.example.logisticaliot.GetDataVolley.GetLongitude;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;

    Button DataBtn,ReloadBtn,ControlBtn;

    String strJson = "";
    double A, B, Kinhdo, Vido;
    String url = "https://iotlogistics.000webhostapp.com/App/getdata.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        DataBtn = findViewById(R.id.Data_Btn);
//        ReloadBtn = findViewById(R.id.ReloadBtn);
        ControlBtn = findViewById(R.id.Control_Btn);
//        GetData(url);

//        GetLongitude f = new GetLongitude();
//        f.getJSONArray(MapActivity.this,A);
        ControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });
        DataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });
        
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        
//        ReloadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent();
//                intent.setClass(MapActivity.this, MapActivity.class);
//                startActivity(intent);
//            }
//        });
        
    }
    
    @Override
    public void onMapReady(GoogleMap map) {
//        A = 9.722166;
//        B = 105.649804;
        mMap = map;
        RequestQueue queue = Volley.newRequestQueue(this);
        new CountDownTimer(1000000000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, Urls.GETDATA_URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // chuyen mang thanh chuoi
                        for (int i=response.length()-1; i<response.length(); i++){
                            try{
                                JSONObject person = response.getJSONObject(i);
                                Kinhdo = Double.parseDouble(person.getString("Kinh do"));
                                Vido = Double.parseDouble(person.getString("Vi do"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i=response.length(); i>=0; i--){
                            try{
                                JSONObject person = response.getJSONObject(i);
                                String Ten = person.getString("Ten");
                                String XuatPhat = person.getString("Xuat phat");
                                String DichDen = person.getString("Dich den");
                                //dua vao chuoi
                                LatLng xe1 = new LatLng(Kinhdo,Vido);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(xe1, 14));
                                mMap.addMarker(new MarkerOptions()
                                        .title("Xuất phát từ "+XuatPhat+" đến "+DichDen)
                                        .snippet("Tên tài xế: "+Ten)
                                        .position(xe1));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                Toast.makeText(MapActivity.this,response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MapActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(req);
            }

            @Override
            public void onFinish() {

            }
        }.start();


    }

    //    public void GetData(String url){
//        RequestQueue queue = Volley.newRequestQueue(this);
//        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                // chuyen mang thanh chuoi
//                for (int i=response.length()-1; i<response.length(); i++){
//                    try{
//                        JSONObject person = response.getJSONObject(i);
//                        Kinhdo = Double.parseDouble(person.getString("Kinh do"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                A = 9.722166;
//                B = 105.649804;
////                Toast.makeText(MapActivity.this,response.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(MapActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        queue.add(req);
//    }
}