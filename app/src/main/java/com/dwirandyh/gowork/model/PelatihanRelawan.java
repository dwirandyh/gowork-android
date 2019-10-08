package com.dwirandyh.gowork.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PelatihanRelawan {
    @SerializedName("idTunaKarya")
    @Expose
    private String idTunaKarya;
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

    public String getIdTunaKarya() {
        return idTunaKarya;
    }

    public void setIdTunaKarya(String idTunaKarya) {
        this.idTunaKarya = idTunaKarya;
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
