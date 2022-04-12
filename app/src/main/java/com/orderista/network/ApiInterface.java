package com.orderista.network;

import com.orderista.models.RegistrationResponse;
import com.orderista.models.SignInResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("registration1.php")
    Call<RegistrationResponse>registerUser(@FieldMap HashMap<String,String> parameters);
    @FormUrlEncoded
    @POST("sign_in.php")
    Call<SignInResponse>signIn(@Field("email")String email,@Field("password")String password);
}
