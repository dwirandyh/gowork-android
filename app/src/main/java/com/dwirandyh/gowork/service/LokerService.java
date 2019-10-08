package com.dwirandyh.gowork.service;

import com.dwirandyh.gowork.model.Loker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LokerService {
    @GET("api/loker")
    Call<List<Loker>> getAll();

    @GET("api/loker/{id}")
    Call<Loker> getDetail(@Path(value = "id", encoded = true) int id);
}
