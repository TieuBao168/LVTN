package com.example.logisticaliot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.logisticaliot.GetDataVolley.GetInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView LVThongtin;
    ArrayList<ThongTin> arrayThongTin;
    Thongtinadapter adapter;
    Button BackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        BackBtn = findViewById(R.id.btn_back);
        LVThongtin = (ListView) findViewById(R.id.LvThongtin);
        arrayThongTin = new ArrayList<>();

        adapter = new Thongtinadapter(this, R.layout.activity_history_view, arrayThongTin);
        LVThongtin.setAdapter(adapter);

        Getdata(LoginActivity.GetData_Url);

        new CountDownTimer(1000000000,10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Getdata(LoginActivity.GetData_Url);
            }
            @Override
            public void onFinish() {

            }
        }.start();

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HistoryActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Getdata(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = response.length() ; i >= 0 ; i--){
                            try {

                                JSONObject object = response.getJSONObject(i);

                                arrayThongTin.add(new ThongTin(
                                        object.getString("Nhiet do"),
                                        object.getString("Do am"),
//                                        object.getString("Anh sang"),
                                        object.getString("Thoi gian doc")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
//                        Toast.makeText(HistoryActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(HistoryActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
