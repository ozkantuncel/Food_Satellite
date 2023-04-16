package com.example.foodsatellite.data.remote

import com.example.foodsatellite.domain.model.MenuResponse
import retrofit2.http.GET

interface MenuApi {
    @GET("tumYemekleriGetir.php")
    suspend fun getMenu(): MenuResponse
}