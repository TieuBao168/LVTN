package com.example.logisticaliot.GetDataVolley;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.logisticaliot.LoginActivity;
import com.example.logisticaliot.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetInformation {
    String strJson = "";
    public void getJSONArray(Context context, TextView textView){
        // 1.Khởi tạo request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2.truyền đường dẫn vào request
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, LoginActivity.GetData_Url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // chuyen mang thanh chuoi
                for (int i=response.length(); i>=0 ; i--){
                    try{
                        JSONObject person = response.getJSONObject(i);
                        String Ten = person.getString("Ten");
                        String DienThoai = person.getString("Dien Thoai");
                        String XuatPhat = person.getString("Xuat phat");
                        String DichDen = person.getString("Dich den");
                        String ThoiGian = person.getString("Thoi gian");
                        //dua vao chuoi
                        if((Ten.isEmpty())||(DienThoai.isEmpty())||(XuatPhat.isEmpty())||(DichDen.isEmpty())||(ThoiGian.isEmpty())){
                            continue;
                        }else {
                            strJson += "Tên: " + Ten + "\n";
                            strJson += "SĐT: " + DienThoai + "\n";
                            strJson += "Xuất phát: " + XuatPhat + "\n";
                            strJson += "Đích đến: " + DichDen + "\n";
                            strJson += "Thời gian khởi hành: " + ThoiGian + "\n";

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (strJson.isEmpty()){
                        continue;
                    }else{
                        textView.setText(strJson);
                        break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.getMessage());
            }
        });
        //4. add
        queue.add(req);
    }
}
