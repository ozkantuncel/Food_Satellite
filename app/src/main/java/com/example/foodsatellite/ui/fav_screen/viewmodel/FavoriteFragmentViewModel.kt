package com.example.foodsatellite.ui.fav_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodsatellite.domain.model.FavoriteMeal
import com.example.foodsatellite.domain.repository.MenuDaoRepository
import com.example.foodsatellite.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val menuDaoRepository: MenuDaoRepository
):ViewModel() {
    private val _myFav = MutableLiveData<Resource<List<FavoriteMeal>>>()
    val myFav: LiveData<Resource<List<FavoriteMeal>>> = _myFav

    init {
        fetchFav()
    }


     fun fetchFav(){
        viewModelScope.launch{
            _myFav.value = Resource.Loading
            try {
                val result = menuDaoRepository.getFavData()
                _myFav.value = result

            }catch (e: Exception){
                Resource.Failure(e.message)
            }
        }
    }


    private fun fetchNameDSCFav(){
        viewModelScope.launch{
            _myFav.value = Resource.Loading
            try {
                val result = menuDaoRepository.getFavMealsByNameDesc()
                _myFav.value = result

            }catch (e: Exception){
                Resource.Failure(e.message)
            }
        }
    }

    private fun fetchPriceASCFav(){
        viewModelScope.launch{
            _myFav.value = Resource.Loading
            try {
                val result = menuDaoRepository.getFavMealsByPriceAsc()
                _myFav.value = result

            }catch (e: Exception){
                Resource.Failure(e.message)
            }
        }
    }

    private fun fetchPriceDSCFav(){
        viewModelScope.launch{
            _myFav.value = Resource.Loading
            try {
                val result = menuDaoRepository.getFavMealsByPriceDsc()
                _myFav.value = result

            }catch (e: Exception){
                Resource.Failure(e.message)
            }
        }
    }



    fun arrBy(sort:String,categories: List<String>){

        when (sort) {
            categories[0] -> {fetchFav()}
            categories[1] -> {fetchNameDSCFav()}
            categories[2] -> {fetchPriceASCFav()}
            categories[3] -> {fetchPriceDSCFav()}
        }
    }

    fun deleteFav(favoriteMeal: FavoriteMeal){
        viewModelScope.launch {
            try {
                menuDaoRepository.deleteFavData(favoriteMeal)
                fetchFav()
            }catch (e:Exception){
                _myFav.value = Resource.Failure(e.message)
            }
        }
    }


}