package com.example.foodsatellite.ui.cart_screen.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsatellite.R
import com.example.foodsatellite.databinding.RowItemsFavouriteBinding
import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.domain.model.FavoriteMeal
import com.example.foodsatellite.ui.fav_screen.FavoriteFragmentDirections
import com.example.foodsatellite.ui.fav_screen.viewmodel.FavoriteFragmentViewModel


class FavoriteFragmentAdapter(
    var mContext: Context,
    private val meals:List<FavoriteMeal>,
    private val viewModel: FavoriteFragmentViewModel
): RecyclerView.Adapter<FavoriteFragmentAdapter.ViewHolder>() {

    private var listData: MutableList<FavoriteMeal> = meals as MutableList<FavoriteMeal>

    inner class ViewHolder(private var binding: RowItemsFavouriteBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: FavoriteMeal){
            binding.mealData = item
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${item.meal_imageName}"
            loadImage(imageUrl = url,binding.imageFood)

            binding.favoriteCard.setOnClickListener {

                val oCart = CartMeal(
                    id = 0,
                    name = item.meal_name,
                    imageName = item.meal_imageName,
                    price = item.meal_price,
                    quantity = 1,
                    username = "ozkantuncel2016@gmail.com"
                )

                val detail = FavoriteFragmentDirections.favFragmentToDetailFragmet(oCart)
                Navigation.findNavController(it).navigate(detail)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowItemsFavouriteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.row_items_favourite,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = listData[position]
        holder.bind(meal)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(index:Int){
        viewModel.deleteFav(listData[index])
        notifyDataSetChanged()
    }

    fun loadImage(imageUrl:String,imageView: ImageView){
        Glide.with(mContext)
            .load(imageUrl)
            .placeholder(R.drawable.progress_circular)
            .into(imageView)
    }


}