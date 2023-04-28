package com.example.foodsatellite.ui.main_screen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsatellite.R
import com.example.foodsatellite.databinding.RowItemsBinding
import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.domain.model.FavoriteMeal
import com.example.foodsatellite.domain.model.Meal
import com.example.foodsatellite.ui.main_screen.MainFragmentDirections
import com.example.foodsatellite.ui.main_screen.viewmodel.MainFragmentViewModel

class MainFragmentAdapter(
    var mContext:Context,
    private val meals:List<Meal>,
    private val viewModel: MainFragmentViewModel
):RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {
    var onItemClickListener: ((ImageView, Int) -> Unit)? = null

    inner class ViewHolder(private var binding: RowItemsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Meal){
            binding.mealData = item
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${item.imageName}"
            loadImage(imageUrl = url,binding.imageFood)

            binding.imageCart.setOnClickListener{

                val oCart = CartMeal(
                    id = 0,
                    name = item.name,
                    imageName = item.imageName,
                    price = item.price,
                    quantity = 1,
                    username = "ozkantuncel2016@gmail.com"
                )

                val detail = MainFragmentDirections.homeFragmentTodetailFragmet(oCart)
                Navigation.findNavController(it).navigate(detail)

            }


            binding.imageHeart.setOnClickListener {
                val fav = FavoriteMeal(null,item.id,item.name,item.imageName,item.price)
                viewModel.insertFav(fav)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:RowItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.row_items,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)

    }

    fun loadImage(imageUrl:String,imageView: ImageView){
        Glide.with(mContext)
            .load(imageUrl)
            .placeholder(R.drawable.progress_circular)
            .into(imageView)
    }


}