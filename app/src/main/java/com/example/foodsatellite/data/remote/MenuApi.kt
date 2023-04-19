package com.example.foodsatellite.data.remote

import com.example.foodsatellite.domain.model.CartMealResponse
import com.example.foodsatellite.domain.model.CartResponse
import com.example.foodsatellite.domain.model.MenuResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MenuApi {
    @GET("tumYemekleriGetir.php")
    suspend fun getMenu(): MenuResponse


    @POST("sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("yemek_adi") mealName: String,
        @Field("yemek_resim_adi") imageName: String,
        @Field("yemek_fiyat") price: Int,
        @Field("yemek_siparis_adet") quantity: Int,
        @Field("kullanici_adi") username: String
    ): CartResponse


    @POST("sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getUserCart(
        @Field("kullanici_adi") username: String
    ): CartMealResponse

    @POST("sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteCartItem(
        @Field("sepet_yemek_id") cartMealId:Int,
        @Field("kullanici_adi") username:String
    ):CartResponse
}