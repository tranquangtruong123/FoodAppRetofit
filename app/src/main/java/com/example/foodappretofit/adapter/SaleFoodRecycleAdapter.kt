package com.example.foodappretofit.adapter

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodappretofit.databinding.SalefoodItemBinding
import com.example.foodappretofit.pojo.Meal

class SaleFoodRecycleAdapter: RecyclerView.Adapter<SaleFoodRecycleAdapter.SaleFoodViewHoder>() {
    inner class SaleFoodViewHoder(val binding : SalefoodItemBinding):
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleFoodViewHoder {
        return SaleFoodViewHoder(
            SalefoodItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SaleFoodViewHoder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            saleitemTitle.text = product.strMeal
            Glide.with(holder.itemView)
                .load(product.strMealThumb)
                .into(saleitemImg)
        }
        holder.binding.itemLayoutsalefood.setOnClickListener {
                onItemClick?.invoke(product.idMeal)

        }
    }
    var onItemClick: ((id : String) -> Unit)? = null
}