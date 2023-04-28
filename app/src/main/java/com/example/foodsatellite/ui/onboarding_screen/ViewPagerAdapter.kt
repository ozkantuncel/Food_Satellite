package com.example.foodsatellite.ui.onboarding_screen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.foodsatellite.R

class ViewPagerAdapter(private val gContext:Context):PagerAdapter() {


    private val images = intArrayOf(
        R.drawable.onb_one_pic,
        R.drawable.onb_two_pic,
        R.drawable.onb_three_pic
    )

    private val headings = intArrayOf(
        R.string.on_title_one,
        R.string.on_title_two,
        R.string.on_title_three,
    )

    private val descriptions = intArrayOf(
        R.string.desc_one,
        R.string.desc_two,
        R.string.desc_three,
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)

        val slideTitleImage = view.findViewById<ImageView>(R.id.titleImage)
        val slideHeading = view.findViewById<TextView>(R.id.texttitle)
        val slideDescription = view.findViewById<TextView>(R.id.textdeccription)

        slideTitleImage.setImageResource(images[position])
        slideHeading.setText(headings[position])
        slideDescription.setText(descriptions[position])

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}