package com.example.foodsatellite.domain.repository

import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.domain.model.CartResponse
import com.example.foodsatellite.domain.model.Meal
import com.example.foodsatellite.domain.util.Resource

interface MenuRepository {
    suspend fun getMenu(): Resource<List<Meal>>

    suspend fun addMealToCart(meal: Meal, username: String, quantity: Int): Resource<CartResponse>

    suspend fun getUserCart(username:String):Resource<List<CartMeal>>

    suspend fun deleteCartItem(cartItemId:String,username: String):Resource<CartResponse>
}