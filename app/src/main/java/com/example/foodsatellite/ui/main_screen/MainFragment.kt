package com.example.foodsatellite.ui.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.foodsatellite.databinding.FragmentMainBinding
import com.example.foodsatellite.domain.util.Resource
import com.example.foodsatellite.ui.main_screen.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding:FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel

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

                }
                is Resource.Success -> {
                    resources.data?.let {meals->

                    }
                }
                is Resource.Failure -> {

                }
                is Resource.Empty -> {}
            }

        }
    }

}