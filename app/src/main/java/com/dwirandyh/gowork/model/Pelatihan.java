package com.dwirandyh.gowork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pelatihan {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("tanggalPosting")
    @Expose
    private String tanggalPosting;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("tanggalPelatihan")
    @Expose
    private String tanggalPelatihan;
    @SerializedName("kuota")
    @Expose
    private Integer kuota;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("tanggalPelatihanRaw")
    @Expose
    private String tanggalPelatihanRaw;
    @SerializedName("jumlahPesertaTerdaftar")
    @Expose
    private String jumlahPesertaTerdaftar;
    @SerializedName("lokasi")
    @Expose
    private String lokasi;
    @SerializedName("jamPelatihanDari")
    @Expose
    private String jamPelatihanDari;

    @SerializedName("jamPelatihanSampai")
    @Expose
    private String jamPelatihanSampai;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTanggalPosting() {
        return tanggalPosting;
    }

    public void setTanggalPosting(String tanggalPosting) {
        this.tanggalPosting = tanggalPosting;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggalPelatihan() {
        return tanggalPelatihan;
    }

    public void setTanggalPelatihan(String tanggalPelatihan) {
        this.tanggalPelatihan = tanggalPelatihan;
    }

    public Integer getKuota() {
        return kuota;
    }

    public void setKuota(Integer kuota) {
        this.kuota = kuota;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTanggalPelatihanRaw() {
        return tanggalPelatihanRaw;
    }

    public void setTanggalPelatihanRaw(String tanggalPelatihanRaw) {
        this.tanggalPelatihanRaw = tanggalPelatihanRaw;
    }

    public String getJumlahPesertaTerdaftar() {
        return jumlahPesertaTerdaftar;
    }

    public void setJumlahPesertaTerdaftar(String jumlahPesertaTerdaftar) {
        this.jumlahPesertaTerdaftar = jumlahPesertaTerdaftar;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJamPelatihanDari() {
        return jamPelatihanDari;
    }

    public void setJamPelatihanDari(String jamPelatihanDari) {
        this.jamPelatihanDari = jamPelatihanDari;
    }

    public String getJamPelatihanSampai() {
        return jamPelatihanSampai;
    }

    public void setJamPelatihanSampai(String jamPelatihanSampai) {
        this.jamPelatihanSampai = jamPelatihanSampai;
    }
}
