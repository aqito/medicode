package com.example.androidmedicode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity {

    private static String URL_MEDHISTORY = "http://192.168.0.10/android_Api/api_medEvents.php";
    private String ID;
    private TextView Empty_View;
    SessionManager sessionManager;
    List<MedicalEvents> lstMedicalEvents;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        Empty_View = findViewById(R.id.empty_view);

        //user hashmap
        HashMap<String, String> user = sessionManager.getUserDetail();
        ID = user.get(sessionManager.ID);

        lstMedicalEvents = new ArrayList<>();

        new JsonTask().execute("http://www.doc.gold.ac.uk/usr/344/medical-events-api?patient_id=" + ID);


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


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(History.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
//                    Log.d("Response: ", "> " + line);

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            try {

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    String _event_id = Integer.toString(object.getInt("event_id"));
                    String _date = object.getString("date").trim();
                    String _short_description = object.getString("short_description").trim();
                    String _long_description = object.getString("long_description").trim();
                    String _doctor_GMC = Integer.toString(object.getInt("doctor_GMC"));

                    String _date_Trunc = _date.substring(0, 10);//trim date

                    //adding the MedicalEvents to MedicalEvents list
                    lstMedicalEvents.add(new MedicalEvents(
                            ("Event ID: " + _event_id),
                            (_date_Trunc),
                            (_short_description),
                            (_long_description),
                            ("Doctor GMC: " + _doctor_GMC),
                            R.drawable.blank_prof_pic
                    ));
                }

                if (!(lstMedicalEvents.isEmpty())) {
                    RecyclerView myrv = findViewById(R.id.recyclerview_id);

                    RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(History.this, lstMedicalEvents);

                    myrv.setLayoutManager(new GridLayoutManager(History.this, 3));

                    myrv.setAdapter(myAdapter);
                } else {
                    Empty_View.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("tagconvertstr", "[" + e.toString() + "]");
                Empty_View.setVisibility(View.VISIBLE);
            }
        }
    }


}


