package com.example.foodsatellite.ui.cart_screen.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsatellite.R
import com.example.foodsatellite.databinding.RowItemsCartBinding
import com.example.foodsatellite.domain.model.CartMeal
import com.example.foodsatellite.ui.cart_screen.viewmodel.CartFragmentViewModel


class CartFragmentAdapter(
    var mContext: Context,
    private val meals:List<CartMeal>,
    private val viewModel: CartFragmentViewModel
): RecyclerView.Adapter<CartFragmentAdapter.ViewHolder>() {

    private var listData: MutableList<CartMeal> = meals as MutableList<CartMeal>

    inner class ViewHolder(private var binding: RowItemsCartBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: CartMeal){
            binding.mealData = item
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${item.imageName}"
            loadImage(imageUrl = url,binding.imageFood)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowItemsCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.row_items_cart,
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
        viewModel.deleteCartItem(cartMealId = meals[index].id,username = meals[index].username)
        //viewModel.getUserCart(username = "ozkantuncel2016@gmail.com")
        //meals.
        notifyDataSetChanged()
    }

    fun loadImage(imageUrl:String,imageView: ImageView){
        Glide.with(mContext)
            .load(imageUrl)
            .placeholder(R.drawable.progress_circular)
            .into(imageView)
    }


}