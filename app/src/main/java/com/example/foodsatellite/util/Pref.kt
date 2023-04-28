package com.example.foodsatellite.util

interface Pref {
    fun <T> put(key: String, value: T)

    fun <T> get(key: String): T?

    fun <T> get(key: String, defaultValue: T): T

    fun delete(key: String)

    fun init()
}