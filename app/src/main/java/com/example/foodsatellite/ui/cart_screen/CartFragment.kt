package com.example.foodsatellite.ui.cart_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodsatellite.databinding.FragmentCartBinding
import com.example.foodsatellite.domain.util.Resource
import com.example.foodsatellite.ui.cart_screen.adapter.CartFragmentAdapter
import com.example.foodsatellite.ui.cart_screen.adapter.SwipeToDeleteCallback
import com.example.foodsatellite.ui.cart_screen.viewmodel.CartFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:CartFragmentViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getUserCart(username = "ozkantuncel2016@gmail.com")
        viewModel.cart.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    resources.data?.let { meals ->

                        val adapter = CartFragmentAdapter(requireContext(), meals,viewModel)

                        for (meal in meals){
                            println(meal.username)
                            println(meals.size)
                        }


                        binding.recyclerViewHor.apply {
                            binding.cartAdapter = adapter
                            val swipeDel = object : SwipeToDeleteCallback(requireContext()){
                                override fun onSwiped(
                                    viewHolder: RecyclerView.ViewHolder,
                                    direction: Int
                                ) {
                                    adapter.deleteItem(viewHolder.layoutPosition)
                                }
                            }
                            val touchHelper = ItemTouchHelper(swipeDel)
                            touchHelper.attachToRecyclerView(this)
                        }
                    }
                }

                is Resource.Failure -> {
                    binding.progressCircular.visibility = View.GONE
                    print(resources.error)
                }

                is Resource.Empty -> {
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }
    }






}