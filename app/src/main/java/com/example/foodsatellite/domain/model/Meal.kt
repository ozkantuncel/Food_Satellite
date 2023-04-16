package com.example.foodsatellite.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Meal(@SerializedName("yemek_id") val id: String,
                @SerializedName("yemek_adi") val name: String,
                @SerializedName("yemek_resim_adi") val imageName: String,
                @SerializedName("yemek_fiyat") val price: String):Serializable
