package com.example.androidmedicode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity {

    private static String URL_MEDHISTORY = "http://192.168.0.10/android_Api/api_medEvents.php";
    private String ID;
    SessionManager sessionManager;

    public List<MedicalEvents> lstMedicalEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        //user hashmap
        HashMap<String, String> user = sessionManager.getUserDetail();
        ID = user.get(sessionManager.ID);

        lstMedicalEvents = new ArrayList<>();

        //method to get medical history
        medicalHistory(ID);

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstMedicalEvents);

        myrv.setLayoutManager(new GridLayoutManager(this, 2));

        myrv.setAdapter(myAdapter);


        bottomNav();

    }

    private void bottomNav() {
        //Initialize & assign var
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set profile as selected
        bottomNavigationView.setSelectedItemId(R.id.history);


        //perform itemselectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(), Scan.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void medicalHistory(final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MEDHISTORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Log.i("tagconvertstr", "["+ response+"]");

                            JSONObject jsonObject = new JSONObject(response);
                            int successNo = Integer.parseInt(jsonObject.getString("success"));
                            JSONArray jsonArray = jsonObject.getJSONArray("medHistory");


                            Log.i("tagconvertstr", "[" + jsonArray + "]");


                            //if login is successful
                            if (successNo > 0) {
                                //for jsonarray length
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    //get first jsonobject
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    //set variables to object.

                                    //medEvents
                                    String _event_id = Integer.toString(object.getInt("event_id"));
                                    String _date = object.getString("date").trim();
                                    String _short_description = object.getString("short_description").trim();
                                    String _long_description = object.getString("long_description").trim();
                                    String _doctor_GMC = Integer.toString(object.getInt("doctor_GMC"));

                                    //adding the product to product list
                                    lstMedicalEvents.add(new MedicalEvents(
                                            (_event_id),
                                            (_date),
                                            (_short_description),
                                            (_long_description),
                                            (_doctor_GMC),
                                            R.drawable.blank_prof_pic
                                    ));

                                }
                            } else if (successNo == 0) {
                                //failed login error message
                                Toast.makeText(History.this, "No events", Toast.LENGTH_SHORT).show();
                            } else if (successNo == 404) {
                                //failed login error message
                                Toast.makeText(History.this, "No user with that email", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(History.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(History.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}


