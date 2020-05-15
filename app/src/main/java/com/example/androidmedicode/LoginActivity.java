package com.example.androidmedicode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://192.168.1.44/android_Medicode_API/api_Login.php";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sessionManager = new SessionManager(this);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString();
                String mPass = password.getText().toString();
                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                    Login(mEmail, mPass);
                } else {
                    email.setError("Please insert email.");
                    password.setError("Please insert password");
                }
            }
        });

    }

    private void Login(final String email, final String password) {
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            //if login is successful
                            if (success.equals("1")) {
                                //for jsonarray length
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    Log.i("tagconvertstr", "[" + response + "]");

                                    //get first jsonobject
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    //set variables to object.

                                    //patients
                                    String id = Integer.toString(object.getInt("id")).trim();
                                    String firstname = object.getString("firstname").trim();
                                    String surname = object.getString("surname").trim();
                                    String email = object.getString("email").trim();
                                    String qrcode = object.getString("qrcode").trim();
                                    String token = object.getString("token").trim();

                                    //clinicalrecords
                                    String medical_condition = object.getString("medical_condition").trim();
                                    String date_of_birth = object.getString("date_of_birth").trim();
                                    String gender = object.getString("gender").trim();
                                    String allergies = object.getString("allergies").trim();
                                    String prescriptions = object.getString("prescriptions").trim();
                                    String blood_type = object.getString("blood_type").trim();

                                    //address
                                    String address_line1 = object.getString("address_line1").trim();
                                    String address_line2 = object.getString("address_line2").trim();
                                    String country = object.getString("country").trim();
                                    String city = object.getString("city").trim();
                                    String postcode = object.getString("postcode").trim();

                                    //physicalcondition
                                    String height = Integer.toString(object.getInt("height"));
                                    String weight = Integer.toString(object.getInt("weight"));
                                    String systolic = Integer.toString(object.getInt("systolic"));
                                    String diastolic = Integer.toString(object.getInt("diastolic"));
                                    String smoker = Integer.toString(object.getInt("smoker"));

                                    if (smoker.contains("0")) {
                                        smoker = "No";
                                    } else if (smoker.contains("1")) {
                                        smoker = "Yes";
                                    }

                                    //create session
                                    sessionManager.createSession(id, firstname, surname, email, qrcode, token,
                                            medical_condition, date_of_birth, gender, allergies, prescriptions, blood_type,
                                            height, weight, systolic, diastolic, smoker,
                                            address_line1, address_line2, country, city, postcode);


                                    //go to the scan page
                                    Intent intent = new Intent(LoginActivity.this, Scan.class);

                                    //passing to intent values.
                                    intent.putExtra("id", id);
                                    intent.putExtra("firstname", firstname);
                                    intent.putExtra("surname", surname);
                                    startActivity(intent);

                                    loading.setVisibility(View.GONE);
                                }
                            } else if (success.equals("0")) {
                                //failed login error message
                                Toast.makeText(LoginActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                                btn_login.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);
                            } else if (success.equals("404")) {
                                //failed login error message
                                Toast.makeText(LoginActivity.this, "No user with that email", Toast.LENGTH_SHORT).show();
                                btn_login.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                            btn_login.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
