package com.example.foodsatellite.util

import com.example.foodsatellite.di.CoreLibApplication
import com.orhanobut.hawk.Hawk

object HawkImpl : Pref {
    override fun <T> get(key: String): T? {
        return Hawk.get<T>(key)
    }

    override fun <T> put(key: String, value: T) {
        Hawk.put(key, value)
    }

    override fun <T> get(key: String, defaultValue: T): T {
        return Hawk.get(key, defaultValue)
    }

    override fun delete(key: String) {
        Hawk.delete(key)
    }

    override fun init() {
        Hawk.init(CoreLibApplication.applicationContext()).build()
    }
}