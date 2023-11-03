package com.example.foodappretofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodappretofit.databinding.FavoriteItemBinding

import com.example.foodappretofit.pojo.MealDB

class FavoriteRecycleAdapter: RecyclerView.Adapter<FavoriteRecycleAdapter.FavoriteRecycleViewHoder>() {

    inner class FavoriteRecycleViewHoder(val binding: FavoriteItemBinding):
            RecyclerView.ViewHolder(binding.root)
    private val diffCallBack = object : DiffUtil.ItemCallback<MealDB>(){
        override fun areItemsTheSame(oldItem: MealDB, newItem: MealDB): Boolean {
            return oldItem.mealId == newItem.mealId
        }

        override fun areContentsTheSame(oldItem: MealDB, newItem: MealDB): Boolean {
           return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecycleViewHoder {
        return FavoriteRecycleViewHoder(
            FavoriteItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteRecycleViewHoder, position: Int) {
        val mealDb = differ.currentList[position]
        holder.binding.apply {
            txtFavoritetitle.text = mealDb.mealName
            Glide.with(holder.itemView)
                .load(mealDb.mealThumb)
                .into(imgFavoriteimg)
        }
    }


}