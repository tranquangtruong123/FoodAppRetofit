package com.example.foodappretofit.pojo


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MealList(
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealThumb: String,
    val strYoutube: String
): Parcelable