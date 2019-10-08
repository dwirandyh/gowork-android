package com.dwirandyh.gowork.service;

import com.dwirandyh.gowork.model.Pelatihan;
import com.dwirandyh.gowork.model.PelatihanRelawan;
import com.dwirandyh.gowork.model.PelatihanTunaKarya;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PelatihanService {
    @GET("api/pelatihan")
    Call<List<Pelatihan>> getAll();

    @GET("api/pelatihan/{id}")
    Call<Pelatihan> getDetail(@Path(value = "id", encoded = true) int id);

    @POST("api/pelatihan/tunakarya")
    @FormUrlEncoded
    Call<PelatihanTunaKarya> tambahJadwalTunaKarya(@Field("idPelatihan") int idPelatihan, @Field("idTunaKarya") int idTunaKarya);

    @GET("api/pelatihan/tunakarya/{id}")
    Call<List<Pelatihan>> jadwalPelatihanTunaKarya(@Path(value = "id", encoded = true) int id);

    @POST("api/pelatihan/relawan")
    @FormUrlEncoded
    Call<PelatihanRelawan> tambahJadwalRelawan(@Field("idPelatihan") int idPelatihan, @Field("idRelawan") int idTunaKarya);

    @GET("api/pelatihan/relawan/{id}")
    Call<List<Pelatihan>> jadwalPelatihanRelawan(@Path(value = "id", encoded = true) int id);
}
