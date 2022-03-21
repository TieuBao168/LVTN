package com.example.logisticaliot;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private String Tag = "LoginActivityLog";
    EditText TaikhoanText,MatkhauText,EmailText;
    Button LoginBtn, RegisterBtn;
    ProgressDialog progressDialog;
    public static String GetData_Url = "", Control_Url ="", ControlSetup_Url="";

//    String url="https://iotlogistics.000webhostapp.com/App/register.php";
//    String urls ="https://iotlogistics.000webhostapp.com/App/login.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(Tag, "OnCreate");

        setContentView(R.layout.activity_login);
        LoginBtn = findViewById(R.id.Loginbtn);
        RegisterBtn = findViewById(R.id.Registerbtn);
        TaikhoanText = findViewById(R.id.Taikhoantext);
        MatkhauText = findViewById(R.id.Matkhautext);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRegistrationProcess();
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLoginProcces();
            }
        });
    }

    private void UserLoginProcces() {
        String name = TaikhoanText.getText().toString().trim();
        String password = MatkhauText.getText().toString().trim();
        if (name.isEmpty()||password.isEmpty()){
            message("không được để trống!!!");
        }else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Urls.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("status");
                                if (result.equals("success1")){
                                    GetData_Url=Urls.GETDATA1_URL;
                                    Control_Url=Urls.CONTROL1_URL;
                                    ControlSetup_Url=Urls.CONTROLSETUP1_URL;
                                    progressDialog.dismiss();
//                                    message("Thanhf coong");

                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MapActivity.class);
                                    startActivity(intent);

                                }else if(result.equals("success2")){
                                    GetData_Url=Urls.GETDATA2_URL;
                                    Control_Url=Urls.CONTROL2_URL;
                                    ControlSetup_Url=Urls.CONTROLSETUP2_URL;
                                    progressDialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MapActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    progressDialog.dismiss();
                                    message("tài khoản hoặc mật khẩu không chính xác");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    message(error.getMessage());
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name",name);
                    params.put("password",password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(request);
        }
    }

    private void UserRegistrationProcess() {
        LayoutInflater inflater = getLayoutInflater();
        View activity_register = inflater.inflate(R.layout.activity_register, null);
        final EditText Name = activity_register.findViewById(R.id.reg_taikhoan);
        final EditText Email = activity_register.findViewById(R.id.reg_email);
        final EditText Password = activity_register.findViewById(R.id.reg_matkhau);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(activity_register);
        builder.setTitle("Đăng ký");
        builder.setPositiveButton("Đăng ký", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                progressDialog.show();
                final String name = Name.getText().toString().trim();
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();

                if (name.isEmpty()||email.isEmpty()||password.isEmpty()){
                    message("Không được để trống đâu :v");
                    progressDialog.dismiss();
                }else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.REGISTER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    message(response);
                                    progressDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            message(error.getMessage());
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("name", name);
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(stringRequest);
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void message(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Tag,"onStart");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(Tag,"onPostResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Tag,"onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(Tag,"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Tag,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Tag,"onDestroy");
    }
}
