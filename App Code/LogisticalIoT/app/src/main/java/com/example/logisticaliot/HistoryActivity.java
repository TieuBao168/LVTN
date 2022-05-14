package com.example.logisticaliot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView LVThongtin;
    ArrayList<ThongTin> arrayThongTin, arrayThongTin1, arrayThongTin2, arrayThongTin3, arrayThongTin4;
    ArrayList<String> list;
    Thongtinadapter adapter, adapter1, adapter2, adapter3, adapter4;
    Button BackBtn;
    Spinner SpinnerSensor, SpinnerDay;
    String Timest, day, Pre_day, time, set_day="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history_list);
        BackBtn = findViewById(R.id.btn_back);
        LVThongtin = (ListView) findViewById(R.id.LvThongtin);
        SpinnerSensor = (Spinner)findViewById(R.id.spinnersensor);
//        SpinnerDay = (Spinner)findViewById(R.id.spinnerday);

        arrayThongTin = new ArrayList<>();
        arrayThongTin1 = new ArrayList<>();
        arrayThongTin2 = new ArrayList<>();
        arrayThongTin3 = new ArrayList<>();
        arrayThongTin4 = new ArrayList<>();

        ArrayList<String> arraySensor = new ArrayList<String>();
        arraySensor.add("Trung bình");
        arraySensor.add("Cảm biến 1");
        arraySensor.add("Cảm biến 2");
        arraySensor.add("Cảm biến 3");
        arraySensor.add("Cảm biến 4");




//        Getdata(LoginActivity.GetSensor_Url, "Nhiet do", "Do am");

        adapter = new Thongtinadapter(this, R.layout.activity_history_view, arrayThongTin);
        adapter1 = new Thongtinadapter(this, R.layout.activity_history_view, arrayThongTin1);
        adapter2 = new Thongtinadapter(this, R.layout.activity_history_view, arrayThongTin2);
        adapter3 = new Thongtinadapter(this, R.layout.activity_history_view, arrayThongTin3);
        adapter4 = new Thongtinadapter(this, R.layout.activity_history_view, arrayThongTin4);
//        LVThongtin.setAdapter(adapter);

        ArrayAdapter arrayAdapterSensor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySensor);
        arrayAdapterSensor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSensor.setAdapter(arrayAdapterSensor);
        SpinnerSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

                        Getdata(LoginActivity.GetSensor_Url, "Nhiet do", "Do am", arrayThongTin, adapter);
                        LVThongtin.setAdapter(adapter);
                        break;
                    case 1:
                        Getdata(LoginActivity.GetSensor_Url, "Nhiet do 1", "Do am 1", arrayThongTin1, adapter1);
                        LVThongtin.setAdapter(adapter1);
                        break;
                    case 2:
                        Getdata(LoginActivity.GetSensor_Url, "Nhiet do 2", "Do am 2", arrayThongTin2, adapter2);
                        LVThongtin.setAdapter(adapter2);
                        break;
                    case 3:
                        Getdata(LoginActivity.GetSensor_Url, "Nhiet do 3", "Do am 3", arrayThongTin3, adapter3);
                        LVThongtin.setAdapter(adapter3);
                        break;
                    case 4:
                        Getdata(LoginActivity.GetSensor_Url, "Nhiet do 4", "Do am 4", arrayThongTin4, adapter4);
                        LVThongtin.setAdapter(adapter4);
                        break;
                    default:
                        break;
                        }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HistoryActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });

    }

    private void Getdata(String url, String temp, String humi, ArrayList thongtin, Thongtinadapter adapt){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        thongtin.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = response.length() - 1; i >= 0 ; i--){
                            try {

                                JSONObject object = response.getJSONObject(i);

                                thongtin.add(new ThongTin(
                                        object.getString(temp),
                                        object.getString(humi),
//                                        object.getString("Anh sang"),
                                        object.getString("Thoi gian doc")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapt.notifyDataSetChanged();
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
//
//    public class CustomSpinner extends androidx.appcompat.widget.AppCompatSpinner
//    {
//        public CustomSpinner(Context context)
//        {
//            super(context);
//        }
//
//        public CustomSpinner(Context context, AttributeSet attrs)
//        {
//            super(context, attrs);
//        }
//
//        public CustomSpinner(Context context, AttributeSet attrs, int defStyle)
//        {
//            super(context, attrs, defStyle);
//        }
//
//        public CustomSpinner(Context context, AttributeSet attrs, int defStyle, int mode)
//        {
//            super(context, attrs, defStyle, mode);
//        }
//
//        public CustomSpinner(Context context, int mode)
//        {
//            super(context, mode);
//        }
//
//        @Override
//        public boolean performClick()
//        {
//            boolean bClicked = super.performClick();
//
//            try
//            {
//                Field mPopupField = Spinner.class.getDeclaredField("mPopup");
//                mPopupField.setAccessible(true);
//                ListPopupWindow pop = (ListPopupWindow) mPopupField.get(this);
//                ListView listview = pop.getListView();
//
//                Field mScrollCacheField = View.class.getDeclaredField("mScrollCache");
//                mScrollCacheField.setAccessible(true);
//                Object mScrollCache = mScrollCacheField.get(listview);
//                Field scrollBarField = mScrollCache.getClass().getDeclaredField("scrollBar");
//                scrollBarField.setAccessible(true);
//                Object scrollBar = scrollBarField.get(mScrollCache);
//                Method method = scrollBar.getClass().getDeclaredMethod("setVerticalThumbDrawable", Drawable.class);
//                method.setAccessible(true);
//                method.invoke(scrollBar, getResources().getDrawable(R.drawable.scrollbar_style));
//
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//                {
//                    Field mVerticalScrollbarPositionField = View.class.getDeclaredField("mVerticalScrollbarPosition");
//                    mVerticalScrollbarPositionField.setAccessible(true);
//                    mVerticalScrollbarPositionField.set(listview, SCROLLBAR_POSITION_LEFT);
//                }
//            }
//            catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//
//            return bClicked;
//        }
//    }
//
//    private void SpinnerFilterDay(String url) {
//        list = new ArrayList<>();
//
//        // 1.Khởi tạo request
//        RequestQueue queue = Volley.newRequestQueue(this);
//        // 2.truyền đường dẫn vào request
//        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                // chuyen mang thanh chuoi
//                Pre_day = day = "";
//
//                for (int i = response.length() - 1; i >= 0; i--) {
//                    try {
//                        JSONObject person = response.getJSONObject(i);
//                        Timest = person.getString("Thoi gian doc");
//                        String[] Spl = Timest.split(" ");
//                        day = Spl[0];
//                        if(!(day.equals(Pre_day))){
//                            list.add(day);
//                            Pre_day=day;
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                ShowSpinnerFilterDay(list);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        queue.add(req);
//    }
//
//    private void ShowSpinnerFilterDay(List<String> list){
//        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        SpinnerDay.setAdapter(adapter);
//        SpinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                set_day = list.get(position);
//                GetChart(URL, Lable, typeData, set_day);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                set_day= list.get(0);
//                GetChart(URL, Lable, typeData, set_day);
//            }
//        });
//    }
}
