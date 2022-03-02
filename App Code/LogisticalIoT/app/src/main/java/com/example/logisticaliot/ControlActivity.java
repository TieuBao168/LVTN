package com.example.logisticaliot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ControlActivity extends AppCompatActivity {

    LineChart lineChart_Nhietdo, lineChart_Doam;
    Float Nhietdo, Doam;
    Button MapBtn,ReloadBtn,DataBtn;

    int[] colorClassArray = new int[] {Color.BLUE, Color.RED};
    String[] legendName = {"Cam bien 1", "cam bien 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        MapBtn = findViewById(R.id.Map_Btn);
        ReloadBtn = findViewById(R.id.Reload_Btn);
        DataBtn = findViewById(R.id.DataBtn);

        lineChart_Nhietdo = findViewById(R.id.lineChart);
        lineChart_Doam = findViewById(R.id.lineChart1);

            // 1.Khởi tạo request
        RequestQueue queue = Volley.newRequestQueue(this);
            // 2.truyền đường dẫn vào request
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, Urls.GETDATA_URL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                lineChart_Nhietdo.setDragEnabled(true);
                lineChart_Nhietdo.setScaleEnabled(false);

                ArrayList<Entry> dataSet_Nhietdo = new ArrayList<Entry>();
                for (int i=response.length()-10; i<response.length(); i++) {
                    try {
                        JSONObject person = response.getJSONObject(i);
                        Nhietdo = Float.parseFloat(person.getString("Nhiet do"));
                        dataSet_Nhietdo.add(new Entry(i, Nhietdo));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                LineDataSet lineDataSet_Nhietdo = new LineDataSet(dataSet_Nhietdo, "Nhiệt độ");
                ArrayList<ILineDataSet> iLineDataSets_Nhietdo = new ArrayList<>();
                iLineDataSets_Nhietdo.add(lineDataSet_Nhietdo);

                LineData lineData_Nhietdo = new LineData(iLineDataSets_Nhietdo);
                lineChart_Nhietdo.setData(lineData_Nhietdo);
                lineChart_Nhietdo.invalidate();

                //if you want set background color use below method
                //lineChart.setBackgroundColor(Color.RED);

                // set text if data are are not available
                lineChart_Nhietdo.setNoDataText("Data not Available");

                //you can modify your line chart graph according to your requirement there are lots of method available in this library

                //now customize line chart


                lineDataSet_Nhietdo.setColor(Color.RED);
                lineDataSet_Nhietdo.setCircleColor(Color.GREEN);
                lineDataSet_Nhietdo.setDrawCircles(false);
                //lineDataSet.setDrawCircleHole(true);
                lineDataSet_Nhietdo.setLineWidth(3);
                //lineDataSet.setCircleRadius(10);
                //lineDataSet.setCircleHoleRadius(10);
                lineDataSet_Nhietdo.setValueTextSize(13);
                lineDataSet_Nhietdo.setValueTextColor(Color.BLACK);

                Legend legend_Nhietdo = lineChart_Nhietdo.getLegend();
                legend_Nhietdo.setTextSize(15);
                legend_Nhietdo.setForm(Legend.LegendForm.LINE);
                legend_Nhietdo.setXEntrySpace(15);
                legend_Nhietdo.setFormSize(17);
                legend_Nhietdo.setFormToTextSpace(5);

                Description descrip_Nhietdo = new Description();
                descrip_Nhietdo.setText("Nhiệt độ");
                descrip_Nhietdo.setTextColor(Color.BLACK);
                descrip_Nhietdo.setTextSize(16);
                lineChart_Nhietdo.setDescription(descrip_Nhietdo);


                //Edit Độ ẩm chart
                lineChart_Doam.setDragEnabled(true);
                lineChart_Doam.setScaleEnabled(false);

                ArrayList<Entry> dataSet_Doam = new ArrayList<Entry>();
                for (int i=response.length()-10; i<response.length(); i++) {
                    try {
                        JSONObject a = response.getJSONObject(i);
                        Doam = Float.parseFloat(a.getString("Do am"));
                        dataSet_Doam.add(new Entry(i, Doam));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                LineDataSet lineDataSet_Doam = new LineDataSet(dataSet_Doam, "Độ ẩm");
                ArrayList<ILineDataSet> iLineDataSets_Doam = new ArrayList<>();
                iLineDataSets_Doam.add(lineDataSet_Doam);

                LineData lineData_Doam = new LineData(iLineDataSets_Doam);
                lineChart_Doam.setData(lineData_Doam);
                lineChart_Doam.invalidate();

                //if you want set background color use below method
                //lineChart.setBackgroundColor(Color.RED);

                // set text if data are are not available
                lineChart_Doam.setNoDataText("Data not Available");

                //you can modify your line chart graph according to your requirement there are lots of method available in this library

                //now customize line chart

                lineDataSet_Doam.setColor(Color.BLUE);
                lineDataSet_Doam.setCircleColor(Color.GREEN);
                lineDataSet_Doam.setDrawCircles(false);
                //lineDataSet.setDrawCircleHole(true);
                lineDataSet_Doam.setLineWidth(3);
                //lineDataSet.setCircleRadius(10);
                //lineDataSet.setCircleHoleRadius(10);
                lineDataSet_Doam.setValueTextSize(13);
                lineDataSet_Doam.setValueTextColor(Color.BLACK);

                Legend legend_Doam = lineChart_Doam.getLegend();
                legend_Doam.setTextSize(15);
                legend_Doam.setForm(Legend.LegendForm.LINE);
                legend_Doam.setXEntrySpace(15);
                legend_Doam.setFormSize(17);
                legend_Doam.setFormToTextSpace(5);

                Description descrip_Doam = new Description();
                descrip_Doam.setText("Độ ẩm");
                descrip_Doam.setTextColor(Color.BLACK);
                descrip_Doam.setTextSize(16);
                lineChart_Doam.setDescription(descrip_Doam);

                LegendEntry[] legendEntries_Doam = new LegendEntry[2];

                for(int i=0; i<legendEntries_Doam.length; i++){
                    LegendEntry entry = new LegendEntry();
                    entry.formColor = colorClassArray[i];
                    entry.label = String.valueOf(legendName[i]);
                    legendEntries_Doam[i] = entry;
                }
//                legend_Doam.setCustom(legendEntries_Doam);

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
