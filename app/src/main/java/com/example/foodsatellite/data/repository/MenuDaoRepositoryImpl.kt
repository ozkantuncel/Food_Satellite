package com.example.foodsatellite.data.repository

import com.example.foodsatellite.data.data_source.MenuDao
import com.example.foodsatellite.domain.model.FavoriteMeal
import com.example.foodsatellite.domain.repository.MenuDaoRepository
import com.example.foodsatellite.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MenuDaoRepositoryImpl @Inject constructor(
    private val menuDao: MenuDao
) : MenuDaoRepository {
    override suspend fun getFavData(): Resource<List<FavoriteMeal>> {
        return try {
            val myDataList = menuDao.getFavMeals()
            Resource.Success(myDataList)
        } catch (e: Exception) {
            Resource.Failure("Error occurred")
        }
    }

    override suspend fun deleteFavData(favoriteMeal: FavoriteMeal) {
        withContext(Dispatchers.IO) {
            menuDao.deleteMeal(favoriteMeal)
        }
    }

    override suspend fun insertFavData(favoriteMeal: FavoriteMeal) {
        withContext(Dispatchers.IO) {
            menuDao.insertUniqueMeal(favoriteMeal)
        }
    }

    override suspend fun getFavMealsByNameDesc(): Resource<List<FavoriteMeal>> {
        return try {
            val myDataList = menuDao.getFavMealsByNameDesc()
            Resource.Success(myDataList)
        } catch (e: Exception) {
            Resource.Failure("Error occurred")
        }
    }

    override suspend fun getFavMealsByPriceAsc(): Resource<List<FavoriteMeal>> {
        return try {
            val myDataList = menuDao.getFavMealsByPriceAsc()
            Resource.Success(myDataList)
        } catch (e: Exception) {
            Resource.Failure("Error occurred")
        }
    }

    override suspend fun getFavMealsByPriceDsc(): Resource<List<FavoriteMeal>> {
        return try {
            val myDataList = menuDao.getFavMealsByPriceDesc()
            Resource.Success(myDataList)
        } catch (e: Exception) {
            Resource.Failure("Error occurred")
        }
    }

    override suspend fun getFavMealsCount(): Resource<Int> {
        return try {
            val favCount = withContext(Dispatchers.IO){
                menuDao.getFavMealsCount()
            }

            Resource.Success(favCount)
        }catch (e:Exception){
            Resource.Failure(e.message)
        }
    }

    override suspend fun checkIfMealExists(name: String): Boolean {
        return withContext(Dispatchers.IO){
            menuDao.checkIfMealExists(name)
        }
    }


}