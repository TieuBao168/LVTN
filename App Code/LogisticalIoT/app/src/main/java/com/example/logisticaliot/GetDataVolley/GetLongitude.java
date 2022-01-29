package com.example.logisticaliot.GetDataVolley;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class GetLongitude {
    Array strJson;
    public void getJSONArray(Context context, Double A){
        // 1.Khởi tạo request
        RequestQueue queue = Volley.newRequestQueue(context);
        // 2.truyền đường dẫn vào request
        String url = "http://192.168.1.11/Demo_app/getdata.php";
        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // chuyen mang thanh chuoi
                for (int i=response.length()-1; i<response.length(); i++){
                    try{
                        JSONObject person = response.getJSONObject(i);
                        Double KinhDo = person.getDouble("Kinh do");
                        //dua vao chuoi
                        A.doubleValue();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        //4. add
        queue.add(req);
    }
}
