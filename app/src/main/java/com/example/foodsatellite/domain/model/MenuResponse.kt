package com.example.foodsatellite.domain.model

import com.example.foodsatellite.domain.model.Meal
import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("yemekler") val meals: List<Meal>,
    @SerializedName("success") val success: Int
)