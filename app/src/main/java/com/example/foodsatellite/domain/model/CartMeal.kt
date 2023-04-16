package com.example.foodsatellite.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CartMeal(
    @SerializedName("sepet_yemek_id")
    val id: Int,
    @SerializedName("yemek_adi")
    val name: String,
    @SerializedName("yemek_resim_adi")
    val imageName: String,
    @SerializedName("yemek_fiyat")
    val price: Int,
    @SerializedName("yemek_siparis_adet")
    val quantity: Int,
    @SerializedName("kullanici_adi")
    val username: String
): Serializable