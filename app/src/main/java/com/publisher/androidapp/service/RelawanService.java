package com.publisher.androidapp.service;

import com.publisher.androidapp.model.Relawan;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RelawanService {
    @POST("api/relawan/login")
    @FormUrlEncoded
    Call<Relawan> login(@Field("email") String email, @Field("password") String password);

    @GET("api/relawan/{id}")
    Call<Relawan> getDetail(@Path(value = "id", encoded = true) int id);

    @POST("api/relawan")
    @FormUrlEncoded
    Call<Relawan> registrasi(@FieldMap Map<String, String> fields);
}
