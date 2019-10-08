package com.publisher.androidapp.service;

import com.publisher.androidapp.model.Berita;
import com.publisher.androidapp.model.Forum;
import com.publisher.androidapp.model.Jawaban;
import com.publisher.androidapp.model.Relawan;
import com.publisher.androidapp.model.TambahJawaban;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ForumService {
    @GET("api/forum")
    Call<List<Forum>> getAll();

    @GET("api/forum/tunakarya/{id}")
    Call<List<Forum>> getForumTunaKarya(@Path(value = "id", encoded = true) int id);

    @POST("api/forum")
    @FormUrlEncoded
    Call<Relawan> add(@FieldMap Map<String, String> fields);

    @GET("api/forum/{id}")
    Call<List<Jawaban>> getJawaban(@Path(value = "id", encoded = true) int id);

    @POST("api/forum/{id}")
    @FormUrlEncoded
    Call<TambahJawaban> add(@Path(value = "id", encoded = true) int id, @FieldMap Map<String, String> fields);
}
