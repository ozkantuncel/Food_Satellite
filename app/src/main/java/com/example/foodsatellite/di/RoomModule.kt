package com.example.foodsatellite.di

import android.content.Context
import androidx.room.Room
import com.example.foodsatellite.data.data_source.MenuDao
import com.example.foodsatellite.data.data_source.MenuDataBase
import com.example.foodsatellite.data.repository.MenuDaoRepositoryImpl
import com.example.foodsatellite.domain.repository.MenuDaoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MenuDataBase {
        return Room.databaseBuilder(
            context,
            MenuDataBase::class.java,
            "fav_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideMyDataDao(database: MenuDataBase): MenuDao {
        return database.menuDao()
    }

    @Provides
    @Singleton
    fun provideMyRepository(myDataDao: MenuDao): MenuDaoRepository {
        return MenuDaoRepositoryImpl(myDataDao)
    }



}