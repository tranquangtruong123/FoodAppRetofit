package com.example.foodappretofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodappretofit.database.MealsDatabase
import com.example.foodappretofit.database.Repository
import com.example.foodappretofit.pojo.MealDB
import com.example.foodappretofit.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FoodViewModelDao(application: Application): AndroidViewModel(application) {
    private  var allMeals: LiveData<List<MealDB>>
    private  var repository: Repository


    init {

        val mealDao = MealsDatabase.getInstance(application).dao()
        repository = Repository(mealDao)
        allMeals = repository.favoriteList

    }
    fun insertMealFavorites(mealDB: MealDB){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertFavoriteMeal(mealDB)
            withContext(Dispatchers.Main){

            }
        }
    }
//    private fun getAllSavedMeal(){
//
//        viewModelScope.launch {
//            allMeals.value = Resource.Loading()
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    repository.favoriteList.value ?: emptyList()
//                }
//                allMeals.value = Resource.Success(response)
//            } catch (ex: Exception) {
//                allMeals.value = Resource.Error(ex.message)
//            }
//        }
//    }
    fun deleteFavoriteMeal(mealDB: MealDB){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFavoriteMeal(mealDB)
        }
    }
    fun deleteMealById(mealId: Int){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteMealById(mealId)
        }
    }

     fun isMealSavedInDatabase(mealId: Int): Boolean{
         var meal: MealDB? = null
         runBlocking(Dispatchers.IO) {
             meal = repository.getMealById(mealId)
         }
         if (meal == null)
             return false
         return true
    }
    fun observeSavedAllFavorite(): LiveData<List<MealDB>>{
        return allMeals
    }
}