package com.example.foodsatellite.data.repository

import com.example.foodsatellite.data.remote.MenuApi
import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.domain.model.CartResponse
import com.example.foodsatellite.domain.model.Meal
import com.example.foodsatellite.domain.repository.MenuRepository
import com.example.foodsatellite.domain.util.Resource
import java.lang.Exception
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(private val menuApi: MenuApi):MenuRepository {
    override suspend fun getMenu(): Resource<List<Meal>> {
        return try {
            val result = menuApi.getMenu()
            if(result.success == 1){
                Resource.Success(data = result.meals)
            }else{
                Resource.Failure(error = "Hata")
            }
        }catch (e:Exception){
            Resource.Failure(e.message)
        }
    }

    override suspend fun addMealToCart(
        meal: Meal,
        username: String,
        quantity: Int
    ): Resource<CartResponse> {

        return try {
            val result = menuApi.addToCart(
                mealName = meal.name,
                imageName = meal.imageName,
                price = meal.price,
                quantity = quantity,
                username = username
            )
            if(result.success == 1){
                Resource.Success(data = result)
            }else{
                Resource.Failure(error = result.message ?:"Bilinmedik bir hata")
            }
        }catch (e:Exception){
            Resource.Failure(e.message ?:"Bir hata olustu")
        }
    }

    override suspend fun getUserCart(username: String): Resource<List<CartMeal>> {
        return try {
            val result = menuApi.getUserCart(username)
            if(result.success == 1){
                Resource.Success(data = result.meals)
            }else{
                Resource.Failure(error = "Hata")
            }
        }catch (e:Exception){
            Resource.Failure(error = e.message)
        }
    }

    override suspend fun deleteCartItem(cartItemId: Int, username: String): Resource<CartResponse> {
        return try {
            val result = menuApi.deleteCartItem(cartItemId,username)
            if(result.success == 1){
                Resource.Success(data = result)
            }else{
                Resource.Failure(error = result.message ?:"Bilinmedik bir hata")
            }
        }catch (e:Exception){
            Resource.Failure(error = e.message?:"Bir hata olustu")
        }
    }


}