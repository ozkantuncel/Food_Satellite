package com.example.foodsatellite.ui.main_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.foodsatellite.R
import com.example.foodsatellite.databinding.FragmentMainBinding
import com.example.foodsatellite.domain.util.Resource
import com.example.foodsatellite.ui.badge_box.BadgeBox
import com.example.foodsatellite.ui.main_screen.adapter.MainFragmentAdapter
import com.example.foodsatellite.ui.main_screen.viewmodel.MainFragmentViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var badgeBoxInterface: BadgeBox
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel
    private val categories = listOf("İsim Artan","İsim Azalan","Fiyat Artan","Fiyat Azalan")
    private var selectedCategoryIndex = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarMain)

        requireActivity().addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main,menu)

                val item = menu.findItem(R.id.action_search)
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(this@MainFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: MainFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        cratedChip()
        viewModel.meals.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    resources.data?.let { meals ->

                        val adapter = MainFragmentAdapter(requireContext(), meals, viewModel)
                        binding.mainAdapter = adapter
                    }

                }

                is Resource.Failure -> {
                    println(resources.error)
                }

                is Resource.Empty -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.recyclerViewHor.adapter = null
                }
            }
        }

        viewModel.favCount()
        viewModel.myFav.observe(viewLifecycleOwner){resources->
            when (resources) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    resources.data?.let { meals ->
                        sendNumberToActivityFav(meals)
                    }
                }

                is Resource.Failure -> {}

                is Resource.Empty -> { sendNumberToActivityFav(0)}
            }

        }

        viewModel.getUserCart(username = "ozkantuncel2016@gmail.com")
        viewModel.cart.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    resources.data?.let { meals ->
                        sendNumberToActivity(meals.size)
                    }
                }

                is Resource.Failure -> {}

                is Resource.Empty -> {sendNumberToActivity(0)}
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

        viewModel.arrBy(selectedChip.text.toString())

        selectedCategoryIndex = index

    }

    fun sendNumberToActivity(number: Int) {
        badgeBoxInterface.onNumberCart(number)
    }

    fun sendNumberToActivityFav(number: Int) {
        badgeBoxInterface.onNumberFavorite(number)

    }

    override fun onQueryTextSubmit(query: String): Boolean {
       search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        search(newText)
        return true
    }

    fun search(query: String){
        viewModel.searchMeals(query)
    }


}