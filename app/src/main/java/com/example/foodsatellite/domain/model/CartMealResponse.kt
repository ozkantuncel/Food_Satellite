package com.example.foodsatellite.domain.model

import com.google.gson.annotations.SerializedName

data class CartMealResponse(
    @SerializedName("sepet_yemekler") val meals: List<CartMeal>,
    @SerializedName("success") val success: Int
)
