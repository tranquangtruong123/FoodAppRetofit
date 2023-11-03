package com.example.foodappretofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodappretofit.databinding.CategoryfoodItemBinding
import com.example.foodappretofit.databinding.CategorylistItemBinding
import com.example.foodappretofit.pojo.Category


class CategoryFoodRecycleAdapter: RecyclerView.Adapter<CategoryFoodRecycleAdapter.CategoryFoodViewHoder>() {
    inner class CategoryFoodViewHoder(val binding: CategoryfoodItemBinding):
        RecyclerView.ViewHolder(binding.root)
    private val diffCallBack = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFoodViewHoder {
        return CategoryFoodViewHoder(
            CategoryfoodItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryFoodViewHoder, position: Int) {
        val category = differ.currentList[position]
        holder.binding.apply {
            txtCategorytitle.text = category.strCategory
            Glide.with(holder.itemView)
                .load(category.strCategoryThumb)
                .into(imgCategoryimg)

        }
        holder.binding.itemLayoutcategory.setOnClickListener {
            onItemClick?.invoke(category)
        }

    }
    var onItemClick: ((Category) -> Unit)? = null
}