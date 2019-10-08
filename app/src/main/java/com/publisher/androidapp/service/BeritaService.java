package com.publisher.androidapp.service;

import com.publisher.androidapp.model.Berita;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BeritaService {
    @GET("api/berita")
    Call<List<Berita>> getAll();

    @GET("api/berita/{id}")
    Call<Berita> getDetail(@Path(value = "id", encoded = true) int id);
}
