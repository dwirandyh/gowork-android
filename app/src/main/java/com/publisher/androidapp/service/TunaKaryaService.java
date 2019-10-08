package com.publisher.androidapp.service;

import com.publisher.androidapp.model.Berita;
import com.publisher.androidapp.model.TunaKarya;

import java.util.List;
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
