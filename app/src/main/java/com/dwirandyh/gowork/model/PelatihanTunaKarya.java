package com.dwirandyh.gowork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PelatihanTunaKarya {
    @SerializedName("idRelawan")
    @Expose
    private String idRelawan;
    @SerializedName("idPelatihan")
    @Expose
    private String idPelatihan;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getIdRelawan() {
        return idRelawan;
    }

    public void setIdRelawan(String idRelawan) {
        this.idRelawan = idRelawan;
    }

    public String getIdPelatihan() {
        return idPelatihan;
    }

    public void setIdPelatihan(String idPelatihan) {
        this.idPelatihan = idPelatihan;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
