package com.example.foodsatellite.ui.cart_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodsatellite.R
import com.example.foodsatellite.databinding.FragmentCartBinding
import com.example.foodsatellite.domain.util.Resource
import com.example.foodsatellite.ui.badge_box.BadgeBox
import com.example.foodsatellite.ui.cart_screen.adapter.CartFragmentAdapter
import com.example.foodsatellite.ui.util.SwipeToDeleteCallback
import com.example.foodsatellite.ui.cart_screen.viewmodel.CartFragmentViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var badgeBoxInterface: BadgeBox
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartFragmentViewModel
    private val categories = listOf("İsim Artan","İsim Azalan","Fiyat Artan","Fiyat Azalan")
    private var selectedCategoryIndex = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        viewModel.getUserCart(username = "ozkantuncel2016@gmail.com")
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:CartFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cratedChip()
        viewModel.cart.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }

                is Resource.Success -> {

                    binding.progressCircular.visibility = View.GONE
                    resources.data?.let { meals ->

                        sendNumberToActivity(meals.size)
                        val adapter = CartFragmentAdapter(requireContext(), meals,viewModel)

                        binding.recyclerViewHor.apply {
                            binding.cartAdapter = adapter
                            val swipeDel = object : SwipeToDeleteCallback(requireContext()){
                                override fun onSwiped(
                                    viewHolder: RecyclerView.ViewHolder,
                                    direction: Int
                                ) {
                                    selectChip(0)
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
                    binding.recyclerViewHor.adapter = null
                    println(resources.error)
                }

                is Resource.Empty -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.recyclerViewHor.adapter = null
                    sendNumberToActivity(0)

                }
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BadgeBox) {
            badgeBoxInterface = context
        } else {
            throw RuntimeException("$context must implement MyInterface")
        }
    }

    fun cratedChip(){
        for(i in categories.indices){
            val chip = layoutInflater.inflate(R.layout.chip_item, null, false) as Chip
            chip.text = categories[i]
            chip.isClickable = true
            chip.isChecked = i == selectedCategoryIndex

            chip.setOnClickListener{
                selectChip(i)
            }
            binding.chipGroup.addView(chip)

            if(selectedCategoryIndex == 0){
                selectChip(0)
            }
        }
    }

    private fun selectChip(index: Int) {

        val previousChip = binding.chipGroup.getChildAt(selectedCategoryIndex) as Chip
        previousChip.isChecked = false
        previousChip.setChipBackgroundColorResource(R.color.chipGray)

        val selectedChip = binding.chipGroup.getChildAt(index) as Chip
        selectedChip.isChecked = true
        selectedChip.setChipBackgroundColorResource(R.color.chipTextPress)

        viewModel.arrBy(selectedChip.text.toString(),"ozkantuncel2016@gmail.com")

        selectedCategoryIndex = index

    }


    fun sendNumberToActivity(number: Int) {
        badgeBoxInterface.onNumberCart(number)

    }



}