package com.example.androidmedicode;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("//patient-login-api")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

}
