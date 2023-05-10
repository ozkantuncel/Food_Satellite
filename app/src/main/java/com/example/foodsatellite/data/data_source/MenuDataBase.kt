package com.example.foodsatellite.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodsatellite.domain.model.FavoriteMeal

@Database(entities = [FavoriteMeal::class], version = 1)
abstract class MenuDataBase: RoomDatabase() {
    abstract fun menuDao():MenuDao

    companion object {
        @Volatile
        private var INSTANCE: MenuDataBase? = null


    }
}