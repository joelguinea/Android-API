package com.joel.midterm_guineaandroid.api;

import com.joel.midterm_guineaandroid.pojos.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestPlaceholder {

    @POST("Login")
    <login>
    Call<Login> Login(@Body Login login);
}
