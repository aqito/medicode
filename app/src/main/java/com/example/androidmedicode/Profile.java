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

public class Profile extends AppCompatActivity {

    private TextView name, medical_condition, date_of_birth, gender, allergies, prescriptions, blood_type, height, weight, systolic, diastolic, smoker;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //initialise text view
        name = findViewById(R.id.name);
        medical_condition = findViewById(R.id.medical_condition_2);
        date_of_birth = findViewById(R.id.date_of_birth_2);
        gender = findViewById(R.id.gender_2);
        allergies = findViewById(R.id.allergies_2);
        prescriptions = findViewById(R.id.prescriptions_2);
        blood_type = findViewById(R.id.blood_type_2);
        height = findViewById(R.id.height_2);
        weight = findViewById(R.id.weight_2);
        systolic = findViewById(R.id.systolic_2);
        diastolic = findViewById(R.id.diastolic_2);
        smoker = findViewById(R.id.smoker_2);

        //sessionmanager
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        //user hashmap
        HashMap<String, String> user = sessionManager.getUserDetail();

        //getting user detail from session manager and setting textview
        String ID = user.get(sessionManager.ID);
        String _firstName = user.get(sessionManager.FIRSTNAME);
        String _surname = user.get(sessionManager.SURNAME);
        String _medical_condition = user.get(sessionManager.MEDICAL_CONDITION);
        String _date_of_birth = user.get(sessionManager.DATE_OF_BIRTH);
        String _gender = user.get(sessionManager.GENDER);
        String _allergies = user.get(sessionManager.ALLERGIES);
        String _prescriptions = user.get(sessionManager.PRESCRIPTIONS);
        String _blood_type = user.get(sessionManager.BLOOD_TYPE);
        String _height = user.get(sessionManager.HEIGHT);
        String _weight = user.get(sessionManager.WEIGHT);
        String _systolic = user.get(sessionManager.SYSTOLIC);
        String _diastolic = user.get(sessionManager.DIASTOLIC);
        String _smoker = user.get(sessionManager.SMOKER);

        name.setText(_firstName + " " + _surname);
        medical_condition.setText(_medical_condition);
        date_of_birth.setText(_date_of_birth);
        gender.setText(_gender);
        allergies.setText(_allergies);
        prescriptions.setText(_prescriptions);
        blood_type.setText(_blood_type);
        height.setText(_height);
        weight.setText(_weight);
        systolic.setText(_systolic);
        diastolic.setText(_diastolic);
        smoker.setText(_smoker);

        String profile_URL = "http://doc.gold.ac.uk/usr/344/img/users/profilePictures/" + ID + ".jpg";

        ImageView imageView = (ImageView) findViewById(R.id.scan_profile_Pic);

        Picasso.get().load(profile_URL).resize(250, 250).centerCrop().into(imageView);


        bottomNav();

    }

    private void bottomNav() {
        //Initialize & assign var
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set profile as selected
        bottomNavigationView.setSelectedItemId(R.id.profile);


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
