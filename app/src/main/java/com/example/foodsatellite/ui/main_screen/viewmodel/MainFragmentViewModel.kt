package com.example.foodsatellite.ui.main_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodsatellite.domain.model.CartMeal
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

    private val _cart = MutableLiveData<Resource<List<CartMeal>>>()
    val cart: LiveData<Resource<List<CartMeal>>> = _cart


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

    fun searchMeals(query: String) {
        viewModelScope.launch {
            _meals.value = Resource.Loading
            try {
                val result = menuRepository.getMenu()

                _meals.value = result

                if (query.isBlank()) {
                    return@launch
                }
                val mealsList = (result as? Resource.Success)?.data

                val filteredMeals = mealsList?.filter {

                    it.name.contains(query, ignoreCase = true)

                }

                if (filteredMeals.isNullOrEmpty()) {
                    _meals.value = Resource.Empty
                } else {
                    _meals.value = Resource.Success(filteredMeals)
                }

            } catch (e: Exception) {
                _meals.value = Resource.Failure(e.message)
            }
        }
    }


    fun arrBy(string:String){
        val categories = listOf("İsim Artan","İsim Azalan","Fiyat Artan","Fiyat Azalan")
        viewModelScope.launch {
            _meals.value = Resource.Loading
            try {
                val result = menuRepository.getMenu()
                val mealsList = (result as? Resource.Success)?.data

                val mealListBy = when (string) {
                    categories[0] -> mealsList?.sortedBy { it.name }
                    categories[1] -> mealsList?.sortedByDescending { it.name }
                    categories[2] -> mealsList?.sortedBy { it.price }
                    categories[3] -> mealsList?.sortedByDescending { it.price }
                    else -> null
                }

                if (mealListBy != null) {
                    _meals.value = Resource.Success(mealListBy)
                } else {
                    _meals.value = Resource.Failure("Invalid category")
                }
            } catch (e: Exception) {
                _meals.value = Resource.Failure(e.message)
            }
        }

    }


    fun getUserCart(username: String) {
        viewModelScope.launch {
            _cart.value = Resource.Loading
            try {
                val result = menuRepository.getUserCart(username)
                val cartItems = (result as? Resource.Success)?.data
                if(cartItems != null){
                    _cart.value = result
                }else{
                    _cart.value = Resource.Empty
                }

            } catch (e: Exception) {
                _cart.value = Resource.Failure(e.localizedMessage)
            }
        }
    }





}