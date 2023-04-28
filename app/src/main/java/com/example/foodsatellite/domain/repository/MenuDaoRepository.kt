package com.example.foodsatellite.domain.repository

import com.example.foodsatellite.domain.model.FavoriteMeal
import com.example.foodsatellite.domain.util.Resource

interface MenuDaoRepository {

    suspend fun getFavData(): Resource<List<FavoriteMeal>>

    suspend fun deleteFavData(favoriteMeal: FavoriteMeal)

    suspend fun insertFavData(favoriteMeal: FavoriteMeal)

    suspend fun getFavMealsByNameDesc():Resource<List<FavoriteMeal>>

    suspend fun getFavMealsByPriceAsc():Resource<List<FavoriteMeal>>

    suspend fun getFavMealsByPriceDsc():Resource<List<FavoriteMeal>>

    suspend fun getFavMealsCount():Resource<Int>

    suspend fun checkIfMealExists(name:String):Boolean

}