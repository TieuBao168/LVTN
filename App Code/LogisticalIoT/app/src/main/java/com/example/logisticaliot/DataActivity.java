package com.example.logisticaliot;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.logisticaliot.GetDataVolley.GetInformation;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class DataActivity extends AppCompatActivity {
    Button MapBtn, DataBtn, ControlBtn, HistoryBtn;
    TextView ThongtinView, NhietdoView, DoamView, AnhsangView;
    LineChart lineChart_Nhietdo, lineChart_Doam;
    Float Nhietdo, Doam, Value;
    String URL;

    static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_data);
        ThongtinView = findViewById(R.id.ThongtinView);
        lineChart_Nhietdo = findViewById(R.id.NhietdoChart);
        lineChart_Doam = findViewById(R.id.DoamChart);
//        AnhsangView = findViewById(R.id.AnhsangView);
        MapBtn = findViewById(R.id.Map_Btn);
        DataBtn = findViewById(R.id.Data_Btn);
        ControlBtn = findViewById(R.id.Control_Btn);
        HistoryBtn = findViewById(R.id.History_Btn);
        URL = LoginActivity.GetData_Url;

        GetInformation fetch = new GetInformation();
        fetch.getJSONArray(DataActivity.this,ThongtinView);

        GetChart(URL,lineChart_Nhietdo,"Nhiet do", "Nhiệt độ", Color.RED);
        GetChart(URL,lineChart_Doam,"Do am", "Độ ẩm", Color.BLUE);

        new CountDownTimer(1000000000,5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                GetInformation fetch = new GetInformation();
                fetch.getJSONArray(DataActivity.this,ThongtinView);

                GetChart(URL,lineChart_Nhietdo,"Nhiet do", "Nhiệt độ", Color.RED);
                GetChart(URL,lineChart_Doam,"Do am", "Độ ẩm", Color.BLUE);
            }
            @Override
            public void onFinish() {

            }
        }.start();

        DataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sendNotification();
            }
        });

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

    private void GetChart(String url, LineChart lineChart, String data, String label, Integer color) {
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
                        String preValue = person.getString(data);
                        if(preValue.isEmpty()){
                            dataSet.add(new Entry(i, 0));
                        }else{
                            Value = Float.parseFloat(preValue);
                            dataSet.add(new Entry(i, Value));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                LineDataSet lineDataSet = new LineDataSet(dataSet, label);
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

                lineDataSet.setColor(color);
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
//                legend.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "Set1"});

                Description descrip = new Description();
                descrip.setText(" ");
                descrip.setTextColor(Color.BLACK);
                descrip.setTextSize(16);
                lineChart.setDescription(descrip);

                int[] colorClassArray = new int[] {color};
                String[] legendName = {label};
                LegendEntry[] legendEntries = new LegendEntry[1];
                LegendEntry entry = new LegendEntry();
                entry.formColor = colorClassArray[0];
                entry.label = String.valueOf(legendName[0]);
                legendEntries[0] = entry;
                legend.setCustom(legendEntries);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        //4. add
        queue.add(req);
    }

    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Nhiệt độ giảm đột ngột")
                .setContentText("Message push notification")
                .setSmallIcon(R.drawable.logo)
//                .setLargeIcon(bitmap)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private int getNotificationId(){
        return (int) new Date().getTime();
    }

}
