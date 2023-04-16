package com.example.foodsatellite.ui.main_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodsatellite.domain.model.Meal
import com.example.foodsatellite.domain.repository.MenuRepository
import com.example.foodsatellite.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val menuRepository: MenuRepository) :
    ViewModel() {
    private val _meals = MutableLiveData<Resource<List<Meal>>>()
    val meals: LiveData<Resource<List<Meal>>> = _meals

    init {
        fetchMenu()
    }

    private fun fetchMenu() {
        viewModelScope.launch {
            _meals.value = Resource.Loading
            try {
                val result = menuRepository.getMenu()
                _meals.value = result

            } catch (e: Exception) {
                _meals.value = Resource.Failure(e.message)
            }
        }
    }
}