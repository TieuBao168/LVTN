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

public class GetHumidity {
    String strJson = "";
    public void getJSONArray(Context context, TextView textView){
        // 1.Khởi tạo request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2.truyền đường dẫn vào request
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, LoginActivity.GetData_Url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // chuyen mang thanh chuoi
                for (int i=response.length()-1; i<response.length(); i++){
                    try{
                        JSONObject person = response.getJSONObject(i);
                        String DoAm = person.getString("Do am");
                        //dua vao chuoi
                        strJson += "Độ ẩm: "+DoAm +" %";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    textView.setText(strJson);
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
