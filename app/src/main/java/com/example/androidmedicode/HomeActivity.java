package com.example.androidmedicode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private TextView ID, firstname, surname, email, qrcode, token, medical_condition, date_of_birth, gender, allergies, prescriptions, blood_type, parent1_id, parent2_id, height, weight, systolic, diastolic, smoker;
    private Button btn_logout;
    private static String URL_Details = "http://192.168.0.10/android_register_login/extraDetails.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        ID = findViewById(R.id.id);

        String QR_URL = "http://doc.gold.ac.uk/usr/344/img/users/qrCodes/" + ID + ".png";
        String profile_URL = "http://doc.gold.ac.uk/usr/344/img/users/profilePictures/" + ID + ".jpg";

        ImageView imageView = (ImageView) findViewById(R.id.profile_Pic);
        ImageView imageView2 = (ImageView) findViewById(R.id.QRCODE_Pic);

        Picasso.get().load(QR_URL).resize(100, 100).centerCrop().into(imageView);
        Picasso.get().load(profile_URL).resize(100, 100).centerCrop().into(imageView2);


        firstname = findViewById(R.id.firstname);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        qrcode = findViewById(R.id.qrcode);
        token = findViewById(R.id.token);
        medical_condition = findViewById(R.id.medical_condition);
        date_of_birth = findViewById(R.id.date_of_birth);
        gender = findViewById(R.id.gender);
        allergies = findViewById(R.id.allergies);
        prescriptions = findViewById(R.id.prescriptions);
        blood_type = findViewById(R.id.blood_type);
        parent1_id = findViewById(R.id.parent1_id);
        parent2_id = findViewById(R.id.parent2_id);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        systolic = findViewById(R.id.systolic);
        diastolic = findViewById(R.id.diastolic);
        smoker = findViewById(R.id.smoker);


        btn_logout = findViewById(R.id.btn_logout);

        //user hashmap
        HashMap<String, String> user = sessionManager.getUserDetail();

        String mID = user.get(sessionManager.ID);
        String mFirstName = user.get(sessionManager.FIRSTNAME);
        String mSurname = user.get(sessionManager.SURNAME);
        String mEmail = user.get(sessionManager.EMAIL);

        ID.setText(mID);
        firstname.setText(mFirstName);
        surname.setText(mSurname);
        email.setText(mEmail);

        //call getDetails method
        getDetails(mEmail);



        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });



    }

    private void getDetails(final String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "["+response+"]");

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("extraDetails");

                            //if login is successful
                            if (success.equals("1")) {
                                //for jsonarray length
                                for (int i = 0; i < jsonArray.length(); i++){
                                    //get first jsonobject
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    //set variables to object.
                                    String _qrcode = object.getString("qrcode").trim();
                                    String _token = object.getString("token").trim();
                                    String _medical_condition = object.getString("medical_condition").trim();
                                    String _date_of_birth = object.getString("date_of_birth");
                                    String _gender = object.getString("gender").trim();
                                    String _allergies = object.getString("allergies").trim();
                                    String _prescriptions = object.getString("prescriptions").trim();
                                    String _blood_type = object.getString("blood_type").trim();
                                    String _parent1_id = object.getString("parent1_id").trim();
                                    String _parent2_id = object.getString("parent2_id").trim();
                                    String _height = Integer.toString(object.getInt("height"));
                                    String _weight = Integer.toString(object.getInt("weight"));
                                    String _systolic = Integer.toString(object.getInt("systolic"));
                                    String _diastolic = Integer.toString(object.getInt("diastolic"));
                                    String _smoker = object.getString("smoker").trim();

                                    qrcode.setText(_qrcode);
                                    token.setText(_token);
                                    medical_condition.setText(_medical_condition);
                                    date_of_birth.setText(_date_of_birth);
                                    gender.setText(_gender);
                                    allergies.setText(_allergies);
                                    prescriptions.setText(_prescriptions);
                                    blood_type.setText(_blood_type);
                                    parent1_id.setText(_parent1_id);
                                    parent2_id.setText(_parent2_id);
                                    height.setText(_height);
                                    weight.setText(_weight);
                                    systolic.setText(_systolic);
                                    diastolic.setText(_diastolic);
                                    smoker.setText(_smoker);


                                    //get values from patient
//                                     loading.setVisibility(View.GONE);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
//                             btn_login.setVisibility(View.VISIBLE);
//                             loading.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // loading.setVisibility(View.GONE);
                        // btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(HomeActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
