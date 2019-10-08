package com.dwirandyh.gowork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loker {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("judul")
    @Expose
    private String judul;
    @SerializedName("perusahaan")
    @Expose
    private String perusahaan;
    @SerializedName("rentangGaji")
    @Expose
    private String rentangGaji;
    @SerializedName("bidangKeahlian")
    @Expose
    private String bidangKeahlian;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("tanggalPosting")
    @Expose
    private String tanggalPosting;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getRentangGaji() {
        return rentangGaji;
    }

    public void setRentangGaji(String rentangGaji) {
        this.rentangGaji = rentangGaji;
    }

    public String getBidangKeahlian() {
        return bidangKeahlian;
    }

    public void setBidangKeahlian(String bidangKeahlian) {
        this.bidangKeahlian = bidangKeahlian;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggalPosting() {
        return tanggalPosting;
    }

    public void setTanggalPosting(String tanggalPosting) {
        this.tanggalPosting = tanggalPosting;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
}
