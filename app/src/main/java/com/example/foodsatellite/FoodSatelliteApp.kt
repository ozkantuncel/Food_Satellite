package com.example.foodsatellite

import android.content.Context
import com.example.foodsatellite.di.CoreLibApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FoodSatelliteApp:CoreLibApplication(){

    init {
        instance = this
    }
    companion object{
        lateinit var instance:FoodSatelliteApp

        fun applicationContext():Context{
            return instance.applicationContext
        }
    }
}
