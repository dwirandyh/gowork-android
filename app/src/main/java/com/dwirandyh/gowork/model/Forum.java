package com.dwirandyh.gowork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pertanyaan")
    @Expose
    private String pertanyaan;
    @SerializedName("idTunaKarya")
    @Expose
    private Integer idTunaKarya;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public Integer getIdTunaKarya() {
        return idTunaKarya;
    }

    public void setIdTunaKarya(Integer idTunaKarya) {
        this.idTunaKarya = idTunaKarya;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
