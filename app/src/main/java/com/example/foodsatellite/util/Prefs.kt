package com.example.foodsatellite.util

object Prefs {

    private val pref: Pref = HawkImpl

    init {
        pref.init()
    }

    private const val PREF_KEY_SHOW_ON_BOARDING_STATE = "showOnBoardingState"

    fun isShowedOnBoarding(): Boolean {
        return pref.get(PREF_KEY_SHOW_ON_BOARDING_STATE, false)
    }

    fun setShowOnBoardingState(showed: Boolean) {
        pref.put(PREF_KEY_SHOW_ON_BOARDING_STATE, showed)
    }

}