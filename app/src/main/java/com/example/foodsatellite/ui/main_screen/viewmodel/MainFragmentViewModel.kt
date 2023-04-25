package com.example.foodsatellite.ui.main_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.domain.model.CartResponse
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

    private val _addMealToCart = MutableLiveData<Resource<CartResponse>>()
    val addMealToCart: LiveData<Resource<CartResponse>> = _addMealToCart

    private val _deleteCartMeal = MutableLiveData<Resource<CartResponse>>()
    val deleteCartMeal: LiveData<Resource<CartResponse>> = _deleteCartMeal

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


    fun addMealToCart(meal: Meal, username: String, quantity: Int) {
        viewModelScope.launch {
            _addMealToCart.value = Resource.Loading
            try {
                val result = menuRepository.addMealToCart(meal, username, quantity)
                _addMealToCart.value = result
            } catch (e: Exception) {
                _addMealToCart.value = Resource.Failure(e.message)
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

    fun getMealQuantityInCart(username: String,mealName: String): Int {
        getUserCart(username)
        val cartItems = (_cart.value as? Resource.Success)?.data
        return cartItems?.find { it.name == mealName }?.quantity ?: 0
    }

    fun deleteCartItem(cartMealId: Int, username: String) {
        viewModelScope.launch {
            _deleteCartMeal.value = Resource.Loading
            try {
                val result =
                    menuRepository.deleteCartItem(cartItemId = cartMealId, username = username)
                _deleteCartMeal.value = result
            } catch (e: Exception) {
                _deleteCartMeal.value = Resource.Failure(e.message)
            }
        }
    }


}