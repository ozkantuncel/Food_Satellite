package com.example.foodsatellite.data.remote

import com.example.foodsatellite.domain.model.CartAdd
import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.domain.model.CartMealResponse
import com.example.foodsatellite.domain.model.CartResponse
import com.example.foodsatellite.domain.model.MenuResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MenuApi {
    @GET("/meals/")
    suspend fun getMenu(): MenuResponse


    @POST("/carts/add/")
    suspend fun addToCart(
        @Body cart: CartAdd
    ): CartResponse


    @GET("/carts")
    suspend fun getUserCart(
        @Query("kullanici_adi") username: String
    ): CartMealResponse

    @DELETE("/carts/delete")
    suspend fun deleteCartItem(
        @Query ("sepet_yemek_id") cartMealId:String,
        @Query ("kullanici_adi") username:String
    ):CartResponse
}