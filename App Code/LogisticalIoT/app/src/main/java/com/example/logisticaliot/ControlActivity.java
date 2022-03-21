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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlActivity extends AppCompatActivity {
    Button MapBtn,ReloadBtn, DataBtn, OnOffBtn, OkBtn;
    TextView ThongtinView, NhietdoView, DoamView, MemoView;
    EditText InputValue;
    WebView webView;
    ImageView AirConditionerImg;
    String Handle, Auto, Setup_Aut, Setup_Han;

    int[] img = {R.drawable.airconditioneropen, R.drawable.airconditionerclose};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        MapBtn = findViewById(R.id.Map_Btn);
//        ReloadBtn = findViewById(R.id.Reload_Btn);
//        webView = findViewById(R.id.webview);
        DataBtn = findViewById(R.id.Data_Btn);
        OnOffBtn = findViewById(R.id.OnOff_Btn);
        OkBtn = findViewById(R.id.Ok_Btn);
        InputValue = findViewById(R.id.InputValue);
        AirConditionerImg = findViewById(R.id.AirConditioner_Img);
        MemoView = findViewById(R.id.Memo_Textview);


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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJson().execute(LoginActivity.ControlSetup_Url);
            }
        });

        OnOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OnOffBtn.getText().toString().equals("ON")) {
                    OnOffBtn.setText("OFF");
                    OnOffBtn.setBackgroundColor(Color.RED);
                    Handle = "ON"; Auto ="0";
                    AirConditionerImg.setImageResource(img[0]);
                    Toast.makeText(ControlActivity.this, "Đã mở thiết bị", Toast.LENGTH_SHORT).show();
                    PushControl(Auto, Handle);
                }
                else{
                    OnOffBtn.setText("ON");
                    OnOffBtn.setBackgroundColor(Color.BLUE);
                    Handle = "OFF"; Auto ="0";
                    AirConditionerImg.setImageResource(img[1]);
                    Toast.makeText(ControlActivity.this, "Đã tắt thiết bị", Toast.LENGTH_SHORT).show();
                    PushControl(Auto, Handle);
                }
                MemoView.setText("Chưa thiết lập giá trị !!!");
            }
        });

        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auto = InputValue.getText().toString();
                if(Auto.isEmpty()){
                    Toast.makeText(ControlActivity.this, "Chưa nhập giá trị !!!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ControlActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    MemoView.setText("Giá trị đã thiết lặp: "+Auto+" *C");
                    OnOffBtn.setText("OFF");
                    OnOffBtn.setBackgroundColor(Color.RED);
                    Handle = "ON";
                    AirConditionerImg.setImageResource(img[0]);
                    PushControl(Auto, Handle);
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

    private void PushControl(String Aut, String Han){
        StringRequest request = new StringRequest(Request.Method.POST, LoginActivity.Control_Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Auto",Aut);
                params.put("Handle",Han);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ControlActivity.this);
        queue.add(request);
    }

    class docJson extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... params) {
            return readJson(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject root = new JSONObject(s);
                Setup_Aut = root.getString("Auto");
                Setup_Han = root.getString("Handle");
//                Toast.makeText(ControlActivity.this,Setup_Han, Toast.LENGTH_SHORT).show();
                if(Setup_Aut.equals("0")) {
                    if (Setup_Han.equals("OFF")) {
                        OnOffBtn.setText("ON");
                        OnOffBtn.setBackgroundColor(Color.BLUE);
                        AirConditionerImg.setImageResource(img[1]);
                        MemoView.setText("Chưa thiết lập giá trị !!!");
                    } else {
                        OnOffBtn.setText("OFF");
                        OnOffBtn.setBackgroundColor(Color.RED);
                        AirConditionerImg.setImageResource(img[0]);
                        MemoView.setText("Chưa thiết lập giá trị !!!");
                    }
                }else{
                    MemoView.setText("Giá trị đã thiết lặp: "+Setup_Aut+" *C");
                    OnOffBtn.setText("OFF");
                    OnOffBtn.setBackgroundColor(Color.RED);
                    AirConditionerImg.setImageResource(img[0]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readJson(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
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