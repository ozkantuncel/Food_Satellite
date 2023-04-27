package com.example.foodsatellite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.example.foodsatellite.R
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodsatellite.databinding.ActivityMainBinding
import com.example.foodsatellite.ui.badge_box.BadgeBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),BadgeBox {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        navController = findNavController(R.id.fragmentNav)
        binding.bottomNav.setupWithNavController(navController)


    }

    override fun onNumberCart(number: Int) {
        val badgeDrawable = binding.bottomNav.getOrCreateBadge(R.id.cartFragment)
        if (number > 0) {
            badgeDrawable.isVisible = true
            badgeDrawable.backgroundColor = getColor(R.color.chipTextPress)
            badgeDrawable.number = number
        } else {
            badgeDrawable.isVisible = false
        }
    }

    override fun onNumberFavorite(number: Int) {
        val badgeDrawable = binding.bottomNav.getOrCreateBadge(R.id.favFragment)
        if (number > 0) {
            badgeDrawable.isVisible = true
            badgeDrawable.backgroundColor = getColor(R.color.chipTextPress)
            badgeDrawable.number = number
        } else {
            badgeDrawable.isVisible = false
        }
    }


}