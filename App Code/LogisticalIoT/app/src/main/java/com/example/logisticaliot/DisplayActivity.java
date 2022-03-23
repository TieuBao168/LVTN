package com.example.logisticaliot;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    private MarkerOptions latlng1, latlng2;
    LatLng latlngOne, latlngTwo, xe1;

    Button DataBtn,ReloadBtn,ControlBtn;

    String strJson = "";
    Double A, B, Kinhdo1, Vido1, Kinhdo2, Vido2;

    String url = "https://iotlogistics.000webhostapp.com/App/getdata.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        DataBtn = findViewById(R.id.Data_Btn);
//        ReloadBtn = findViewById(R.id.ReloadBtn);
        ControlBtn = findViewById(R.id.Control_Btn);
//        GetData(url);

//        GetLongitude f = new GetLongitude();
//        f.getJSONArray(MapActivity.this,A);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

//        latlng1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
//        latlng2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");
//
//        latlngOne = new LatLng(27.658143, 85.3199503);
//        latlngTwo = new LatLng(27.667491, 85.3208583);
//
//        String url = getMapsApiDirectionsUrl(latlngOne, latlngTwo);
//        ReadTask downloadTask = new ReadTask();
//        // Start downloading json data from Google Directions API
//        downloadTask.execute(url);

//        float[] results = new float[1];
//        Location.distanceBetween(latlngOne.latitude, latlngTwo.longitude,
//                latlngOne.latitude, latlngTwo.longitude,
//                results);
//        Toast.makeText(DisplayActivity.this,results.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap map) {
//        A = 9.722166;
//        B = 105.649804;
        mMap = map;
//        Log.d("mylog", "Added Markers");
//        mMap.addMarker(latlng1);
//        mMap.addMarker(latlng2);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngOne, 15));

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, Urls.GETDATA1_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // chuyen mang thanh chuoi
                for (int i = response.length(); i>=0; i--) {
                    try {
                        JSONObject person = response.getJSONObject(i);
                        String Kinhdo1s = person.getString("Kinh do xuat phat");
                        Kinhdo1 = Double.parseDouble(Kinhdo1s);
                        String Vido1s = person.getString("Vi do xuat phat");
                        Vido1 = Double.parseDouble(Vido1s);

                        Kinhdo2 = Double.parseDouble(person.getString("Kinh do dich den"));
                        Vido2 = Double.parseDouble(person.getString("Vi do dich den"));
//
////                        Toast.makeText(DisplayActivity.this,"adsf", Toast.LENGTH_SHORT).show();
//
                        latlngOne = new LatLng(Kinhdo1, Vido1);
                        latlngTwo = new LatLng(Kinhdo2, Vido2);

                        if (Vido1s.isEmpty()){
                            continue;
                        }else{
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                LatLng xe1 = new LatLng(Kinhdo1, Vido1);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(xe1, 15));
                mMap.addMarker(new MarkerOptions().position(xe1));

                String url = getMapsApiDirectionsUrl(latlngOne, latlngTwo);
                ReadTask downloadTask = new ReadTask();
                // Start downloading json data from Google Directions API
                downloadTask.execute(url);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MapActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(req);
    }

    private String  getMapsApiDirectionsUrl(LatLng origin,LatLng dest) {
        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+ "&key=" + getString(R.string.map_api_key);
        return url;
    }

    private class ReadTask extends AsyncTask<String, Void , String> {

        @Override
        protected String doInBackground(String... url) {
            // TODO Auto-generated method stub
            String data = "";
            try {
                MapHttpConnection http = new MapHttpConnection();
                data = http.readUr(url[0]);


            } catch (Exception e) {
                // TODO: handle exception
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }

    public class MapHttpConnection {
        public String readUr(String mapsApiDirectionsUrl) throws IOException {
            String data = "";
            InputStream istream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(mapsApiDirectionsUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                istream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(istream));
                StringBuffer sb = new StringBuffer();
                String line ="";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                data = sb.toString();
                br.close();


            }
            catch (Exception e) {
                Log.d("Exception while reading url", e.toString());
            } finally {
                istream.close();
                urlConnection.disconnect();
            }
            return data;
        }
    }

    public class PathJSONParser {

        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;
            try {
                jRoutes = jObject.getJSONArray("routes");
                for (int i=0 ; i < jRoutes.length() ; i ++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List<HashMap<String, String>> path = new ArrayList<HashMap<String,String>>();
                    for(int j = 0 ; j < jLegs.length() ; j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
                        for(int k = 0 ; k < jSteps.length() ; k ++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);
                            for(int l = 0 ; l < list.size() ; l ++){
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("lat",
                                        Double.toString(((LatLng) list.get(l)).latitude));
                                hm.put("lng",
                                        Double.toString(((LatLng) list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;

        }

        private List<LatLng> decodePoly(String encoded) {
            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }
            return poly;
        }}

    private class ParserTask extends AsyncTask<String,Integer, List<List<HashMap<String , String >>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            // TODO Auto-generated method stub
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(4);
                polyLineOptions.color(Color.BLUE);
            }
            if(polyLineOptions!=null) {
                mMap.addPolyline(polyLineOptions);
            }

        }}
}