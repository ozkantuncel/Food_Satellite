package com.example.foodsatellite.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodsatellite.domain.model.FavoriteMeal

@Database(entities = [FavoriteMeal::class], version = 1)
abstract class MenuDataBase: RoomDatabase() {
    abstract fun menuDao():MenuDao

    companion object {
        @Volatile
        private var INSTANCE: MenuDataBase? = null

        fun getDatabase(context: Context): MenuDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenuDataBase::class.java,
                    "fav_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}