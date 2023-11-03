package com.example.foodappretofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodappretofit.databinding.SearchItemBinding
import com.example.foodappretofit.pojo.Meal
import com.example.foodappretofit.pojo.MealList

class SearchFoodRecycleAdapter: RecyclerView.Adapter<SearchFoodRecycleAdapter.SearchFoodRecucleViewHoder>() {

    inner class SearchFoodRecucleViewHoder(val binding: SearchItemBinding):
            RecyclerView.ViewHolder(binding.root)
    private val diffCallBack = object : DiffUtil.ItemCallback<MealList>() {
        override fun areItemsTheSame(oldItem: MealList, newItem: MealList): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealList, newItem: MealList): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFoodRecucleViewHoder {
        return SearchFoodRecucleViewHoder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchFoodRecucleViewHoder, position: Int) {
        val randomFood = differ.currentList[position]
       holder.binding.apply {
           txtSearchtitle.text = randomFood.strMeal
           Glide.with(holder.itemView)
               .load(randomFood.strMealThumb)
               .into(imgSearchimg)
       }
       holder.binding.crtsearchLayout.setOnClickListener {
           onItemClick?.invoke(randomFood.idMeal)
       }
    }
    var onItemClick: ((id : String) -> Unit)? = null
}