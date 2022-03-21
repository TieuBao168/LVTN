package com.example.logisticaliot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.logisticaliot.GetDataVolley.GetHumidity;
import com.example.logisticaliot.GetDataVolley.GetInformation;
import com.example.logisticaliot.GetDataVolley.GetLightIntensity;
import com.example.logisticaliot.GetDataVolley.GetTemperature;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    Button MapBtn, ReloadBtn, ControlBtn, HistoryBtn;
    TextView ThongtinView, NhietdoView, DoamView, AnhsangView;
    LineChart lineChart_Nhietdo, lineChart_Doam;
    Float Nhietdo, Doam;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ThongtinView = findViewById(R.id.ThongtinView);
        lineChart_Nhietdo = findViewById(R.id.NhietdoChart);
        lineChart_Doam = findViewById(R.id.DoamChart);
//        AnhsangView = findViewById(R.id.AnhsangView);
        MapBtn = findViewById(R.id.Map_Btn);
//        ReloadBtn = findViewById(R.id.Reload_Btn);
        ControlBtn = findViewById(R.id.Control_Btn);
        HistoryBtn = findViewById(R.id.History_Btn);
        URL = LoginActivity.GetData_Url;
//        GetInformation f = new GetInformation();
//        f.getJSONArray(DataActivity.this,ThongtinView);
//        GetTemperature t = new GetTemperature ();
//        t.getJSONArray(DataActivity.this,NhietdoView);
//        GetHumidity h = new GetHumidity();
//        h.getJSONArray(DataActivity.this,DoamView);
//        GetLightIntensity l = new GetLightIntensity();
//        l.getJSONArray(DataActivity.this,AnhsangView);

        new CountDownTimer(1000000000,5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                GetInformation f = new GetInformation();
                f.getJSONArray(DataActivity.this,ThongtinView);
//                GetTemperature t = new GetTemperature ();
//                t.getJSONArray(DataActivity.this,NhietdoView);
//                GetHumidity h = new GetHumidity();
//                h.getJSONArray(DataActivity.this,DoamView);
                GetChart(URL,lineChart_Nhietdo,"Nhiet do", Color.RED);
                GetChart(URL,lineChart_Doam,"Do am", Color.BLUE);
            }
            @Override
            public void onFinish() {

            }
        }.start();


//        ReloadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GetInformation f = new GetInformation();
//                // 2. lay mang -> chuyen thanh chuoi
//                f.getJSONArray(DataActivity.this,ThongtinView);
//                GetTemperature t = new GetTemperature ();
//                t.getJSONArray(DataActivity.this,NhietdoView);
//                GetHumidity h = new GetHumidity();
//                h.getJSONArray(DataActivity.this,DoamView);
//                GetLightIntensity l = new GetLightIntensity();
//                l.getJSONArray(DataActivity.this,AnhsangView);
//            }
//        });

        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DataActivity.this, MapActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                intent.setClass(DataActivity.this, ControlActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void GetChart(String url, LineChart lineChart, String type, Integer a) {
        RequestQueue queue = Volley.newRequestQueue(this);
        // 2.truyền đường dẫn vào request
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                lineChart.setDragEnabled(true);
                lineChart.setScaleEnabled(false);

                ArrayList<Entry> dataSet = new ArrayList<Entry>();
                for (int i=response.length()-10; i<response.length(); i++) {
                    try {
                        JSONObject person = response.getJSONObject(i);
                        Nhietdo = Float.parseFloat(person.getString(type));
                        dataSet.add(new Entry(i, Nhietdo));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                LineDataSet lineDataSet = new LineDataSet(dataSet, type);
                ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
                iLineDataSets.add(lineDataSet);

                LineData lineData = new LineData(iLineDataSets);
                lineChart.setData(lineData);
                lineChart.invalidate();

                //if you want set background color use below method
                //lineChart.setBackgroundColor(Color.RED);

                // set text if data are are not available
                lineChart.setNoDataText("Data not Available");

                //you can modify your line chart graph according to your requirement there are lots of method available in this library
                //now customize line chart

                lineDataSet.setColor(a);
//                lineDataSet_Nhietdo.setCircleColor(Color.GREEN);
                lineDataSet.setDrawCircles(false);
                //lineDataSet.setDrawCircleHole(true);
                lineDataSet.setLineWidth(3);
                //lineDataSet.setCircleRadius(10);
                //lineDataSet.setCircleHoleRadius(10);
                lineDataSet.setValueTextSize(13);
                lineDataSet.setValueTextColor(Color.BLACK);

                Legend legend = lineChart.getLegend();
                legend.setTextSize(15);
                legend.setForm(Legend.LegendForm.LINE);
                legend.setXEntrySpace(15);
                legend.setFormSize(17);
                legend.setFormToTextSpace(5);

                Description descrip = new Description();
                descrip.setText(" ");
                descrip.setTextColor(Color.BLACK);
                descrip.setTextSize(16);
                lineChart.setDescription(descrip);

//                LegendEntry[] legendEntries_Doam = new LegendEntry[2];
//                for(int i=0; i<legendEntries_Doam.length; i++){
//                    LegendEntry entry = new LegendEntry();
//                    entry.formColor = colorClassArray[i];
//                    entry.label = String.valueOf(legendName[i]);
//                    legendEntries_Doam[i] = entry;
//                }
//                legend.setCustom(legendEntries_Doam);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        //4. add
        queue.add(req);
    }

//    private void GetData(String url) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        // 2.truyền đường dẫn vào request
//        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                lineChart_Nhietdo.setDragEnabled(true);
//                lineChart_Nhietdo.setScaleEnabled(false);
//
//                ArrayList<Entry> dataSet_Nhietdo = new ArrayList<Entry>();
//                for (int i=response.length()-10; i<response.length(); i++) {
//                    try {
//                        JSONObject person = response.getJSONObject(i);
//                        Nhietdo = Float.parseFloat(person.getString("Nhiet do"));
//                        dataSet_Nhietdo.add(new Entry(i, Nhietdo));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                LineDataSet lineDataSet_Nhietdo = new LineDataSet(dataSet_Nhietdo, "Nhiệt độ");
//                ArrayList<ILineDataSet> iLineDataSets_Nhietdo = new ArrayList<>();
//                iLineDataSets_Nhietdo.add(lineDataSet_Nhietdo);
//
//                LineData lineData_Nhietdo = new LineData(iLineDataSets_Nhietdo);
//                lineChart_Nhietdo.setData(lineData_Nhietdo);
//                lineChart_Nhietdo.invalidate();
//
//                //if you want set background color use below method
//                //lineChart.setBackgroundColor(Color.RED);
//
//                // set text if data are are not available
//                lineChart_Nhietdo.setNoDataText("Data not Available");
//
//                //you can modify your line chart graph according to your requirement there are lots of method available in this library
//
//                //now customize line chart
//
//
//                lineDataSet_Nhietdo.setColor(Color.RED);
//                lineDataSet_Nhietdo.setCircleColor(Color.GREEN);
//                lineDataSet_Nhietdo.setDrawCircles(false);
//                //lineDataSet.setDrawCircleHole(true);
//                lineDataSet_Nhietdo.setLineWidth(3);
//                //lineDataSet.setCircleRadius(10);
//                //lineDataSet.setCircleHoleRadius(10);
//                lineDataSet_Nhietdo.setValueTextSize(13);
//                lineDataSet_Nhietdo.setValueTextColor(Color.BLACK);
//
//                Legend legend_Nhietdo = lineChart_Nhietdo.getLegend();
//                legend_Nhietdo.setTextSize(15);
//                legend_Nhietdo.setForm(Legend.LegendForm.LINE);
//                legend_Nhietdo.setXEntrySpace(15);
//                legend_Nhietdo.setFormSize(17);
//                legend_Nhietdo.setFormToTextSpace(5);
//
//                Description descrip_Nhietdo = new Description();
//                descrip_Nhietdo.setText(" ");
//                descrip_Nhietdo.setTextColor(Color.BLACK);
//                descrip_Nhietdo.setTextSize(16);
//                lineChart_Nhietdo.setDescription(descrip_Nhietdo);
//
//
//                //Edit Độ ẩm chart
//                lineChart_Doam.setDragEnabled(true);
//                lineChart_Doam.setScaleEnabled(false);
//
//                ArrayList<Entry> dataSet_Doam = new ArrayList<Entry>();
//                for (int i=response.length()-10; i<response.length(); i++) {
//                    try {
//                        JSONObject a = response.getJSONObject(i);
//                        Doam = Float.parseFloat(a.getString("Do am"));
//                        dataSet_Doam.add(new Entry(i, Doam));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                LineDataSet lineDataSet_Doam = new LineDataSet(dataSet_Doam, "Độ ẩm");
//                ArrayList<ILineDataSet> iLineDataSets_Doam = new ArrayList<>();
//                iLineDataSets_Doam.add(lineDataSet_Doam);
//
//                LineData lineData_Doam = new LineData(iLineDataSets_Doam);
//                lineChart_Doam.setData(lineData_Doam);
//                lineChart_Doam.invalidate();
//
//                //if you want set background color use below method
//                //lineChart.setBackgroundColor(Color.RED);
//
//                // set text if data are are not available
//                lineChart_Doam.setNoDataText("Data not Available");
//
//                //you can modify your line chart graph according to your requirement there are lots of method available in this library
//
//                //now customize line chart
//
//                lineDataSet_Doam.setColor(Color.BLUE);
//                lineDataSet_Doam.setCircleColor(Color.GREEN);
//                lineDataSet_Doam.setDrawCircles(false);
//                //lineDataSet.setDrawCircleHole(true);
//                lineDataSet_Doam.setLineWidth(3);
//                //lineDataSet.setCircleRadius(10);
//                //lineDataSet.setCircleHoleRadius(10);
//                lineDataSet_Doam.setValueTextSize(13);
//                lineDataSet_Doam.setValueTextColor(Color.BLACK);
//
//                Legend legend_Doam = lineChart_Doam.getLegend();
//                legend_Doam.setTextSize(15);
//                legend_Doam.setForm(Legend.LegendForm.LINE);
//                legend_Doam.setXEntrySpace(15);
//                legend_Doam.setFormSize(17);
//                legend_Doam.setFormToTextSpace(5);
//
//                Description descrip_Doam = new Description();
//                descrip_Doam.setText(" ");
//                descrip_Doam.setTextColor(Color.BLACK);
//                descrip_Doam.setTextSize(16);
//                lineChart_Doam.setDescription(descrip_Doam);
//
////                LegendEntry[] legendEntries_Doam = new LegendEntry[2];
////
////                for(int i=0; i<legendEntries_Doam.length; i++){
////                    LegendEntry entry = new LegendEntry();
////                    entry.formColor = colorClassArray[i];
////                    entry.label = String.valueOf(legendName[i]);
////                    legendEntries_Doam[i] = entry;
////                }
////                legend_Doam.setCustom(legendEntries_Doam);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        //4. add
//        queue.add(req);
//    }


}
