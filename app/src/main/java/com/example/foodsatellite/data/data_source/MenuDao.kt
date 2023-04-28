package com.example.foodsatellite.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.foodsatellite.domain.model.FavoriteMeal

@Dao
interface MenuDao {

    @Query("SELECT * FROM favoritemeal ORDER BY meal_name ASC")
    suspend  fun getFavMeals():List<FavoriteMeal>

    @Query("SELECT * FROM favoritemeal ORDER BY meal_name DESC")
    suspend fun getFavMealsByNameDesc(): List<FavoriteMeal>

    @Query("SELECT * FROM favoritemeal ORDER BY meal_price ASC")
    suspend fun getFavMealsByPriceAsc(): List<FavoriteMeal>

    @Query("SELECT * FROM favoritemeal ORDER BY meal_price DESC")
    suspend fun getFavMealsByPriceDesc(): List<FavoriteMeal>

    @Query("SELECT COUNT(*) FROM favoritemeal")
    suspend fun getFavMealsCount(): Int

    @Query("SELECT EXISTS (SELECT * FROM favoritemeal WHERE meal_name = :name)")
    fun checkIfMealExists(name: String): Boolean

    @Delete
    fun deleteMeal(favoriteMeal: FavoriteMeal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(favoriteMeal: FavoriteMeal)

    @Transaction
    suspend fun insertUniqueMeal(favoriteMeal: FavoriteMeal) {
        val existingMeal = getFavMealByName(favoriteMeal.meal_name)
        if(existingMeal == null) {
            insertMeal(favoriteMeal)
        }
    }


    @Query("SELECT * FROM favoritemeal WHERE meal_name = :mealName LIMIT 1")
    suspend fun getFavMealByName(mealName: String): FavoriteMeal?


}