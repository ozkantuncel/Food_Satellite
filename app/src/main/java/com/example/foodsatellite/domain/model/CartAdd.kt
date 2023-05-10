package com.example.foodsatellite.domain.model

data class CartAdd(
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: Int,
    val yemek_siparis_adet: Int,
    val kullanici_adi: String
)