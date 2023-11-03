package com.example.foodappretofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodappretofit.databinding.CategorylistItemBinding
import com.example.foodappretofit.pojo.Category
import com.example.foodappretofit.pojo.Meal

class CategoryListRecycleAdapter: RecyclerView.Adapter<CategoryListRecycleAdapter.CategoryListRecycleViewHoder>() {
    inner class CategoryListRecycleViewHoder(val binding: CategorylistItemBinding):
            RecyclerView.ViewHolder(binding.root)
    private val differCallback = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    var differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryListRecycleViewHoder {
      return CategoryListRecycleViewHoder(
          CategorylistItemBinding.inflate(
              LayoutInflater.from(parent.context)
          )
      )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryListRecycleViewHoder, position: Int) {
       var meal = differ.currentList[position]
        holder.binding.apply {
            txtCategorytitle.text = meal.strMeal
            Glide.with(holder.itemView)
                .load(meal.strMealThumb)
                .into(imgCategoryimg)
        }
        holder.binding.itemLayoutcategory.setOnClickListener {
            onItemClick?.invoke(meal)
        }
    }
    var onItemClick: ((Meal) -> Unit)? = null
}