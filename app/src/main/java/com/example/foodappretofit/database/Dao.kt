package com.example.foodappretofit.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import com.example.foodappretofit.pojo.MealDB



@Dao
interface Dao {
    @Insert
     fun insertFavorite(mealDB: MealDB)
    @Delete
     fun deleteFavorite(mealDB: MealDB )

    @Query("SELECT * FROM meal_information WHERE mealId = :id")
     fun getMealById(id: Int):MealDB

    @Query("DELETE  FROM meal_information WHERE mealId = :id")
     fun deleteMealById(id: Int)

    @Query("SELECT * FROM meal_information order by mealId asc ")
     fun getAllSavedMeal(): LiveData<List<MealDB>>
}