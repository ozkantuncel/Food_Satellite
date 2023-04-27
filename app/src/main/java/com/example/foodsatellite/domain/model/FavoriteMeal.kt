package com.example.foodsatellite.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMeal(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val meal_id:Int,
    val meal_name:String,
    val meal_imageName:String,
    val meal_price:Int
)