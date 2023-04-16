package com.example.foodsatellite.domain.repository

import com.example.foodsatellite.domain.model.Meal
import com.example.foodsatellite.domain.util.Resource

interface MenuRepository {
    suspend fun getMenu():Resource<List<Meal>>
}