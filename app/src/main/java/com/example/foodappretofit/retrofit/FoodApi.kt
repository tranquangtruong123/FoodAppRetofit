package com.example.foodappretofit.retrofit

import com.example.foodappretofit.pojo.CategoryResponse
import com.example.foodappretofit.pojo.Meal
import com.example.foodappretofit.pojo.MealRandomResponse
import com.example.foodappretofit.pojo.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface FoodApi {

    @GET("random.php")
     fun getRandomMeal():Call<MealRandomResponse>
    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>
     @GET("filter.php?")
    fun getSaleByCategory(@Query("i") category: String ):Call<MealResponse>
    @GET("filter.php?")
    fun getCategoryFood(@Query("c") category: String): Call<MealResponse>
    @GET("lookup.php?")
    fun getFoodByCategory(@Query("i") id : String):Call<MealRandomResponse>
    @GET("search.php?")
    fun getMealByName(@Query("s") s : String):Call<MealRandomResponse>

}