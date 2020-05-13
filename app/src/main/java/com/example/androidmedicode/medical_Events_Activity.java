package com.example.androidmedicode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class medical_Events_Activity extends AppCompatActivity {

    private TextView EventID, Date, ShortDescription, LongDescription, DoctorGMC;
    private ImageView Thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical__events_);

        EventID = findViewById(R.id.medicalEventID);
        Date = findViewById(R.id.medicalEventDate);
        DoctorGMC = findViewById(R.id.doctor_GMC);
        ShortDescription = findViewById(R.id.medicalEventShortDescription);
        LongDescription = findViewById(R.id.medicalEventLongDescription);
        Thumbnail = findViewById(R.id.thumbnail);


        //receive data from card
        Intent intent = getIntent();
        String eventID = intent.getExtras().getString("medicaleventID");
        String doctorGMC = intent.getExtras().getString("doctorGMC");
        String date = intent.getExtras().getString("date");
        String shortDescription = intent.getExtras().getString("shortDescription");
        String longDescription = intent.getExtras().getString("longDescription");
        int image = intent.getExtras().getInt("thumbnail");

        //set values

        EventID.setText(eventID);
        Date.setText(date);
        DoctorGMC.setText(doctorGMC);
        ShortDescription.setText(shortDescription);
        LongDescription.setText(longDescription);
        Thumbnail.setImageResource(image);

    }
}
