package com.publisher.androidapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jawaban {
    @SerializedName("idForum")
    @Expose
    private Integer idForum;
    @SerializedName("idRelawan")
    @Expose
    private String idRelawan;
    @SerializedName("idTunaKarya")
    @Expose
    private String idTunaKarya;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("jawaban")
    @Expose
    private String jawaban;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public Integer getIdForum() {
        return idForum;
    }

    public void setIdForum(Integer idForum) {
        this.idForum = idForum;
    }

    public String getIdRelawan() {
        return idRelawan;
    }

    public void setIdRelawan(String idRelawan) {
        this.idRelawan = idRelawan;
    }

    public String getIdTunaKarya() {
        return idTunaKarya;
    }

    public void setIdTunaKarya(String idTunaKarya) {
        this.idTunaKarya = idTunaKarya;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
