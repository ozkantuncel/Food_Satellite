package com.example.foodsatellite.ui.onboarding_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager
import com.example.foodsatellite.R
import com.example.foodsatellite.ui.MainActivity
import com.example.foodsatellite.util.Prefs.setShowOnBoardingState

class OnboardingActivity : AppCompatActivity() {

    private lateinit var mSLideViewPager: ViewPager
    private lateinit var mDotLayout: LinearLayout
    private lateinit var backbtn: Button
    private lateinit var nextbtn: Button
    private lateinit var skipbtn: Button

    private lateinit var dots: Array<TextView?>
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        backbtn = findViewById(R.id.backbtn)
        nextbtn = findViewById(R.id.nextbtn)
        skipbtn = findViewById(R.id.skipButton)

        backbtn.setOnClickListener {
            if (getItem(0) > 0) {
                mSLideViewPager.setCurrentItem(getItem(-1), true)
            }
        }
        nextbtn.setOnClickListener {
            if (getItem(0) < 2) {
                mSLideViewPager.setCurrentItem(getItem(1), true)
            } else {
                setShowOnBoardingState(showed = true)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        skipbtn.setOnClickListener {
            setShowOnBoardingState(showed = true)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        mSLideViewPager = findViewById(R.id.slideViewPager)
        mDotLayout = findViewById(R.id.indicator_layout)

        viewPagerAdapter = ViewPagerAdapter(this)

        mSLideViewPager.adapter = viewPagerAdapter

        setUpIndicator(0)
        mSLideViewPager.addOnPageChangeListener(viewListener)
    }

    private fun setUpIndicator(position: Int) {
        dots = arrayOfNulls<TextView>(3)
        mDotLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml("&#8226", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            dots[i]?.textSize = 35f
            dots[i]?.setTextColor(
                resources.getColor(
                    R.color.starColor,
                    applicationContext.theme
                )
            )
            mDotLayout.addView(dots[i])
        }

        dots[position]?.setTextColor(
            resources.getColor(
                R.color.chipTextPress,
                applicationContext.theme
            )
        )
    }

    private val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            setUpIndicator(position)

            if (position > 0) {
                backbtn.visibility = View.VISIBLE
            } else {
                backbtn.visibility = View.INVISIBLE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

    private fun getItem(i: Int): Int {
        return mSLideViewPager.currentItem + i
    }
}