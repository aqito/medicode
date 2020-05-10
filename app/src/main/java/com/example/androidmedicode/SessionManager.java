package com.example.androidmedicode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String ID = "ID";
    public static final String FIRSTNAME = "FIRSTNAME";
    public static final String SURNAME = "SURNAME";
    public static final String EMAIL = "EMAIL";
    public static final String QRCODE = "QRCODE";
    public static final String TOKEN = "TOKEN";

    public static final String MEDICAL_CONDITION = "MEDICAL_CONDITION";
    public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
    public static final String GENDER = "GENDER";
    public static final String ALLERGIES = "ALLERGIES";
    public static final String PRESCRIPTIONS = "PRESCRIPTIONS";

    public static final String BLOOD_TYPE = "BLOOD_TYPE";
    public static final String HEIGHT = "HEIGHT";
    public static final String WEIGHT = "WEIGHT";
    public static final String SYSTOLIC = "SYSTOLIC";
    public static final String DIASTOLIC = "DIASTOLIC";
    public static final String SMOKER = "SMOKER";

    public static final String ADDRESS_LINE1 = "ADDRESS_LINE1";
    public static final String ADDRESS_LINE2 = "ADDRESS_LINE2";
    public static final String COUNTRY = "COUNTRY";
    public static final String CITY = "CITY";
    public static final String POSTCODE = "POSTCODE";




    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String firstname, String surname, String email, String qrcode, String token,
                              String medical_condition, String date_of_birth, String gender, String allergies, String prescriptions, String blood_type,
                              String height, String weight, String systolic, String diastolic, String smoker,
                              String address_line1, String address_line2, String country, String city, String postcode) {

        editor.putBoolean(LOGIN, true);


        editor.putString(ID, id);
        editor.putString(FIRSTNAME, firstname);
        editor.putString(SURNAME, surname);
        editor.putString(EMAIL, email);
        editor.putString(QRCODE, qrcode);
        editor.putString(TOKEN, token);

        editor.putString(MEDICAL_CONDITION, medical_condition);
        editor.putString(DATE_OF_BIRTH, date_of_birth);
        editor.putString(GENDER, gender);
        editor.putString(ALLERGIES, allergies);
        editor.putString(PRESCRIPTIONS, prescriptions);
        editor.putString(BLOOD_TYPE, blood_type);

        editor.putString(HEIGHT, height);
        editor.putString(WEIGHT, weight);
        editor.putString(SYSTOLIC, systolic);
        editor.putString(DIASTOLIC, diastolic);
        editor.putString(SMOKER, smoker);

        editor.putString(ADDRESS_LINE1, address_line1);
        editor.putString(ADDRESS_LINE2, address_line2);
        editor.putString(COUNTRY, country);
        editor.putString(CITY, city);
        editor.putString(POSTCODE, postcode);


        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(FIRSTNAME, sharedPreferences.getString(FIRSTNAME, null));
        user.put(SURNAME, sharedPreferences.getString(SURNAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(QRCODE, sharedPreferences.getString(QRCODE, null));
        user.put(TOKEN, sharedPreferences.getString(TOKEN, null));

        user.put(MEDICAL_CONDITION, sharedPreferences.getString(MEDICAL_CONDITION, null));
        user.put(DATE_OF_BIRTH, sharedPreferences.getString(DATE_OF_BIRTH, null));
        user.put(GENDER, sharedPreferences.getString(GENDER, null));
        user.put(ALLERGIES, sharedPreferences.getString(ALLERGIES, null));
        user.put(PRESCRIPTIONS, sharedPreferences.getString(PRESCRIPTIONS, null));
        user.put(BLOOD_TYPE, sharedPreferences.getString(BLOOD_TYPE, null));

        user.put(HEIGHT, sharedPreferences.getString(HEIGHT, null));
        user.put(WEIGHT, sharedPreferences.getString(WEIGHT, null));
        user.put(SYSTOLIC, sharedPreferences.getString(SYSTOLIC, null));
        user.put(DIASTOLIC, sharedPreferences.getString(DIASTOLIC, null));
        user.put(SMOKER, sharedPreferences.getString(SMOKER, null));

        user.put(ADDRESS_LINE1, sharedPreferences.getString(ADDRESS_LINE1, null));
        user.put(ADDRESS_LINE2, sharedPreferences.getString(ADDRESS_LINE2, null));
        user.put(COUNTRY, sharedPreferences.getString(COUNTRY, null));
        user.put(CITY, sharedPreferences.getString(CITY, null));
        user.put(POSTCODE, sharedPreferences.getString(POSTCODE, null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((Settings) context).finish();
    }

}

