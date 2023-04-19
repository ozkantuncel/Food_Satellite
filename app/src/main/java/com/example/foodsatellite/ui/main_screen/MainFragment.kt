package com.example.foodsatellite.ui.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.foodsatellite.databinding.FragmentMainBinding
import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.domain.util.Resource
import com.example.foodsatellite.ui.main_screen.adapter.MainFragmentAdapter
import com.example.foodsatellite.ui.main_screen.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding:FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var listCart:List<CartMeal>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:MainFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         viewModel.meals.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    resources.data?.let {meals->
                        val adapter = MainFragmentAdapter(requireContext(),meals,viewModel)
                        binding.mainAdapter = adapter

                    }

                }
                is Resource.Failure -> {

                }
                is Resource.Empty -> {}
            }
        }

        /*val sampleMeal = Meal(id = 2, name = "Baklava", imageName = "baklava.png", price = 25)
        viewModel.addMealToCart(meal = sampleMeal, username = "ozkantuncel2016@gmail.com", quantity =1 )*/


        //viewModel.deleteCartItem(cartMealId = 94504, username = "ozkantuncel2016@gmail.com")

       // viewModel.getUserCart(username = "ozkantuncel2016@gmail.com")
        viewModel.cart.observe(viewLifecycleOwner){resources->
            when(resources){
                is Resource.Loading -> {
                    println("Loanding")
                }
                is Resource.Success -> {
                     resources.data?.let {meals->
                         listCart = meals
                     }
                }
                is Resource.Failure -> {
                    print(resources.error)
                }
                is Resource.Empty -> {}
            }
        }
    }

}