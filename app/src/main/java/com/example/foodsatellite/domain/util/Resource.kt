package com.example.foodsatellite.domain.util

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    object Empty: Resource<Nothing>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val error: String?): Resource<Nothing>()
}