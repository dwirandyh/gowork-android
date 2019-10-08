package com.dwirandyh.gowork.service;

import com.dwirandyh.gowork.model.Berita;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BeritaService {
    @GET("api/berita")
    Call<List<Berita>> getAll();

    @GET("api/berita/{id}")
    Call<Berita> getDetail(@Path(value = "id", encoded = true) int id);
}
