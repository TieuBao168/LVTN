package com.example.logisticaliot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;

    Button DataBtn,ReloadBtn,ControlBtn;
    Marker marker;
    String strJson = "";
    Double A, B, Kinhdo, Vido;
    String toas, URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        DataBtn = findViewById(R.id.Data_Btn);
//        ReloadBtn = findViewById(R.id.ReloadBtn);
        ControlBtn = findViewById(R.id.Control_Btn);
//        GetData(url);

        URL = LoginActivity.GetData_Url;
//        GetLongitude f = new GetLongitude();
//        f.getJSONArray(MapActivity.this,A);
        ControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, ControlActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        DataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, DataActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // chuyen mang thanh chuoi
                for (int i = response.length() - 1; i < response.length(); i++) {
                    try {
                        JSONObject person = response.getJSONObject(i);
                        Kinhdo = Double.parseDouble(person.getString("Kinh do"));
                        Vido = Double.parseDouble(person.getString("Vi do"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LatLng xe1 = new LatLng(Kinhdo, Vido);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(xe1, 15));
            }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MapActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            });
                queue.add(req);


        RequestQueue queue1 = Volley.newRequestQueue(this);
        new CountDownTimer(1000000000,5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                JsonArrayRequest req1 = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // chuyen mang thanh chuoi
                        for (int i=response.length()-1; i<response.length(); i++){
                            try{
                                JSONObject vitri = response.getJSONObject(i);
                                Kinhdo = Double.parseDouble(vitri.getString("Kinh do"));
                                Vido = Double.parseDouble(vitri.getString("Vi do"));

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

                                if (marker != null){
                                    A = marker.getPosition().latitude;
                                    B = marker.getPosition().longitude;
//                                    toas = A.toString() + "," + Kinhdo.toString()+"|"+B.toString()+","+Vido.toString();
                                    if((!A.equals(Kinhdo))||(!B.equals(Vido))){
//                                        Toast.makeText(MapActivity.this,toas, Toast.LENGTH_SHORT).show();
                                        marker.remove();
                                        marker = mMap.addMarker(new MarkerOptions()
                                                .title("Xuất phát từ " + XuatPhat + " đến " + DichDen)
                                                .snippet("Tên tài xế: " + Ten)
                                                .position(xe1));

                                    }
                                }else {
                                    marker = mMap.addMarker(new MarkerOptions()
                                            .title("Xuất phát từ " + XuatPhat + " đến " + DichDen)
                                            .snippet("Tên tài xế: " + Ten)
                                            .position(xe1));
                                }

//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(xe1, 14));
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
                queue1.add(req1);
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