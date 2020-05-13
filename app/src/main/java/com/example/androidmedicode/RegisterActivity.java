package com.example.androidmedicode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {


    private static String URL_REGISTER = "http://192.168.0.10/android_register_login/extraRegister.php";
    private EditText firstname, surname, email, password, c_password;
    private Button btn_Register;
    private TextView link_login;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        firstname = findViewById(R.id.firstname);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btn_Register = findViewById(R.id.btn_regist);
        link_login = findViewById(R.id.link_login);


        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void Register() {
        loading.setVisibility(View.VISIBLE);
        btn_Register.setVisibility(View.GONE);

        final String firstname = this.firstname.getText().toString().trim();
        final String surname = this.surname.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "[" + response + "]");

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Register failed! --- " + e.toString(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_Register.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register failed!" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_Register.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("firstname", firstname);
                params.put("surname", surname);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

