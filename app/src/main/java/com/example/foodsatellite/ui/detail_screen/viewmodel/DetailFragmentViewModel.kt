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
class DetailFragmentViewModel @Inject constructor(private val menuRepository: MenuRepository) :
    ViewModel() {

    private val _cart = MutableLiveData<Resource<List<CartMeal>>>()
    val cart: LiveData<Resource<List<CartMeal>>> = _cart

    private val _addMealToCart = MutableLiveData<Resource<CartResponse>>()
    val addMealToCart: LiveData<Resource<CartResponse>> = _addMealToCart

    private val _deleteCartMeal = MutableLiveData<Resource<CartResponse>>()
    val deleteCartMeal: LiveData<Resource<CartResponse>> = _deleteCartMeal

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