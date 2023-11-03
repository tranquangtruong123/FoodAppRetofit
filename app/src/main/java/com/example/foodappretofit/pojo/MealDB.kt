package com.example.foodappretofit.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodappretofit.utils.Contract.MEAL_TABLE

@Entity(tableName = MEAL_TABLE)
 data class MealDB(
    @PrimaryKey
    val mealId: Int,
    val mealName: String,
    val mealCountry: String,
    val mealCategory: String,
    val mealInstruction: String,
    val mealThumb: String,
    val mealYoutubeLink: String

)