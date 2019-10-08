package com.publisher.androidapp.service;

import com.publisher.androidapp.model.Berita;
import com.publisher.androidapp.model.Loker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LokerService {
    @GET("api/loker")
    Call<List<Loker>> getAll();

    @GET("api/loker/{id}")
    Call<Loker> getDetail(@Path(value = "id", encoded = true) int id);
}
