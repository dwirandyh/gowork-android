package com.dwirandyh.gowork.service;

import com.dwirandyh.gowork.model.TunaKarya;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TunaKaryaService {
    @POST("api/tunakarya/login")
    @FormUrlEncoded
    Call<TunaKarya> login(@Field("email") String email, @Field("password") String password);

    @GET("api/tunakarya/{id}")
    Call<TunaKarya> getDetail(@Path(value = "id", encoded = true) int id);

    @POST("api/tunakarya")
    @FormUrlEncoded
    Call<TunaKarya> registrasi(@FieldMap Map<String, String> fields);
}
