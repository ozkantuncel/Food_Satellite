package com.example.foodsatellite.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.foodsatellite.R
import com.example.foodsatellite.ui.MainActivity
import com.example.foodsatellite.ui.onboarding_screen.OnboardingActivity
import com.example.foodsatellite.util.Prefs.isShowedOnBoarding

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val backImage:ImageView = findViewById(R.id.sp_logo)
        val sideAnimation= AnimationUtils.loadAnimation(this,R.anim.slide)
        val isShowedOnBoarding = isShowedOnBoarding()
        backImage.startAnimation(sideAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isShowedOnBoarding){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this,OnboardingActivity::class.java))
                finish()
            }

        },3000)
    }
}