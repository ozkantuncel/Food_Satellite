package com.example.foodsatellite.ui.detail_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodsatellite.R
import com.example.foodsatellite.databinding.FragmentDetailBinding
import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.domain.model.Meal
import com.example.foodsatellite.domain.util.Resource
import com.example.foodsatellite.ui.main_screen.viewmodel.DetailFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private  var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val bundle: DetailFragmentArgs by navArgs()
        val data = bundle.cartMeal
        binding.cartMealData = data

        if (data.id != "0"){
            binding.buttonAdd.text = getString(R.string.cart_update_button)
        }


        binding.toolbarDetay.setNavigationOnClickListener {
            nav(data.id,it)
        }

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${data.imageName}"
        loadImage(imageUrl = url, binding.imageViewCart)


        var count = data.quantity

        binding.imageButtonSub.isEnabled = count > 1

        binding.imageButtonAdd.setOnClickListener {
            count++
            updateCountAndButtonStatus(count, data.price)
        }
        binding.imageButtonSub.setOnClickListener {
            if (count > 1) {
                count--
                updateCountAndButtonStatus(count, data.price)
            }
        }



        binding.buttonAdd.setOnClickListener {
            viewModel.getUserCart(username = data.username)
            viewModel.cart.observe(viewLifecycleOwner) { resources ->
                when (resources) {
                    is Resource.Loading -> {
                        //TODO
                    }

                    is Resource.Success -> {
                        println("Success")
                        resources.data?.let { meals ->

                            if (searchInCartMeals(meals, data.name)) {

                                val id = meals.filter { cartMeal ->
                                    cartMeal.name.contains(data.name, ignoreCase = true)
                                }[0]

                                viewModel.deleteCartItem(
                                    cartMealId = id.id,
                                    username = data.username
                                )
                            }
                            val sampleMeal = Meal(
                                id = 0,
                                name = data.name,
                                imageName = data.imageName,
                                price = data.price
                            )
                            viewModel.addMealToCart(
                                meal = sampleMeal,
                                username = data.username,
                                quantity = count
                            )
                        }
                        nav(data.id,it)
                    }

                    is Resource.Failure -> {
                        //TODO
                    }

                    is Resource.Empty -> {
                        println("Empty")
                       val sampleMeal = Meal(
                            id = 0,
                            name = data.name,
                            imageName = data.imageName,
                            price = data.price
                        )
                        viewModel.addMealToCart(
                            meal = sampleMeal,
                            username = data.username,
                            quantity = count
                        )
                        nav(data.id,it)
                    }
                }
            }
        }
    }

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(requireContext())
            .load(imageUrl)
            .placeholder(R.drawable.progress_circular)
            .into(imageView)
    }

    private fun nav(id:String, view: View){
        if (id == "0") {
            Navigation.findNavController(view).navigate(R.id.homeFragment)
        } else {
            Navigation.findNavController(view).navigate(R.id.cartFragment)
        }
    }



    private fun updateCountAndButtonStatus(count: Int, price: Int) {
        binding.textViewCount.text = count.toString()
        val totalPrice = count * price
        binding.textViewPrice.text = getString(R.string.price, totalPrice)
        binding.imageButtonSub.isEnabled = count > 1
    }

    private fun searchInCartMeals(cartList: List<CartMeal>, searchName: String): Boolean {
        if (searchName.isBlank()) {
            return false
        }

        return cartList.any { cartMeal ->
            cartMeal.name.contains(searchName, ignoreCase = true)
        }
    }

}