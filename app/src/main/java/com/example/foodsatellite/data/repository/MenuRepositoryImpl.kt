package com.example.foodsatellite.data.repository

import com.example.foodsatellite.data.remote.MenuApi
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

}