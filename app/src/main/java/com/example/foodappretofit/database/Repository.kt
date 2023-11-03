package com.example.foodappretofit.database

import androidx.lifecycle.LiveData
import com.example.foodappretofit.pojo.MealDB



class Repository(private val mealDao: Dao) {

    val favoriteList: LiveData<List<MealDB>> = mealDao.getAllSavedMeal()
    suspend fun insertFavoriteMeal(mealDB: MealDB){
        mealDao.insertFavorite(mealDB)
    }
    suspend fun deleteFavoriteMeal(mealDB: MealDB){
        mealDao.deleteFavorite(mealDB)
    }
    suspend fun getMealById(mealId: Int): MealDB {
        return mealDao.getMealById(mealId)
    }
     fun deleteMealById(mealId: Int){
        mealDao.deleteMealById(mealId)
    }
}