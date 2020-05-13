package com.example.androidmedicode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class Scan extends AppCompatActivity {

    private TextView message, scan_ID;
    SessionManager sessionManager;
    public List<MedicalEvents> lstMedicalEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        //user hashmap
        HashMap<String, String> user = sessionManager.getUserDetail();

        String ID = user.get(sessionManager.ID);
        String FirstName = user.get(sessionManager.FIRSTNAME);
        String Surname = user.get(sessionManager.SURNAME);


        String welcomeMessage = "Hi " + FirstName + " " + Surname + ",";
        String idMessage = ID;

        message = findViewById(R.id.message);
        scan_ID = findViewById(R.id.scan_ID);

        message.setText(welcomeMessage);
        scan_ID.setText(idMessage);

        String QR_URL = "http://doc.gold.ac.uk/usr/344/img/users/qrCodes/" + ID + ".png";
        String profile_URL = "http://doc.gold.ac.uk/usr/344/img/users/profilePictures/" + ID + ".jpg";

        ImageView imageView = (ImageView) findViewById(R.id.scan_profile_Pic);
        ImageView imageView2 = (ImageView) findViewById(R.id.QRCODE_Pic);

        Picasso.get().load(QR_URL).resize(250, 250).centerCrop().into(imageView);
        Picasso.get().load(profile_URL).resize(250, 250).centerCrop().into(imageView2);


        bottomNav();
    }


    private void bottomNav() {

        //Initialize & assign var
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set scan as selected
        bottomNavigationView.setSelectedItemId(R.id.scan);

        //perform itemselectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.scan:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), History.class));
                        overridePendingTransition(0, 0);
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
}
