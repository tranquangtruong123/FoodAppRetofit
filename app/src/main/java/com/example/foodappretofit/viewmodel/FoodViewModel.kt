package com.example.foodappretofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappretofit.pojo.CategoryResponse
import com.example.foodappretofit.pojo.MealList
import com.example.foodappretofit.pojo.MealRandomResponse
import com.example.foodappretofit.pojo.MealResponse
import com.example.foodappretofit.resource.Resource
import com.example.foodappretofit.retrofit.RetrofitInstrance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel : ViewModel() {
    private val foodMeal = MutableLiveData<Resource<MealResponse>>()
    private val byidfoodcategory = MutableLiveData<Resource<MealRandomResponse>>()
    private val randomfoodCategory = MutableLiveData<Resource<MealRandomResponse>>()
    private val foodCategory = MutableLiveData<Resource<CategoryResponse>>()
    private val categoryList = MutableLiveData<Resource<MealResponse>>()
    private val bynamefoodrandom = MutableLiveData<Resource<MealRandomResponse>>()
    init {
        //getCategories()
        getSaleByCategory("chicken_breast")
        getCategories()
        getRandomByCategory()

    }

     fun getCategoryList(category: String) {
        viewModelScope.launch {
            categoryList.value = Resource.Loading()
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstrance.foodApi.getCategoryFood(category).execute()
                }
                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    categoryList.value = Resource.Success(categoryResponse)
                } else {
                    val errorMessage = response.message()
                    categoryList.value = Resource.Error(errorMessage)
                }
            } catch (t: Throwable) {
                val errorMessage = t.message
                categoryList.value = Resource.Error(errorMessage)
            }
        }
    }

    private fun getRandomByCategory() {
        viewModelScope.launch {
            randomfoodCategory.value = Resource.Loading()
            try {
                val response = withContext(Dispatchers.IO){
                    RetrofitInstrance.foodApi.getRandomMeal().execute()
                }
                if(response.isSuccessful){
                    val randomResponse = response.body()
                    randomfoodCategory.value = Resource.Success(randomResponse)
                }
                else{
                    val errorMessage = response.message()
                    randomfoodCategory.value = Resource.Error(errorMessage)
                }
            }catch (t: Throwable){
                val errorMessage = t.message
                randomfoodCategory.value = Resource.Error(errorMessage)
            }
        }
    }

    private fun getSaleByCategory(category : String) {
        viewModelScope.launch {
            foodMeal.value = Resource.Loading()
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstrance.foodApi.getSaleByCategory(category).execute()
                }
                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    foodMeal.value = Resource.Success(categoryResponse)
                } else {
                    val errorMessage = response.message()
                    foodMeal.value = Resource.Error(errorMessage)
                }
            } catch (t: Throwable) {
                val errorMessage = t.message
                foodMeal.value = Resource.Error(errorMessage)
            }
        }
    }
      fun getFoodbyid(id : String){
              viewModelScope.launch {
                  byidfoodcategory.value = Resource.Loading()
                  try {
                      val response = withContext(Dispatchers.IO) {
                          RetrofitInstrance.foodApi.getFoodByCategory(id).execute()
                      }
                      if (response.isSuccessful) {
                          val mealResponse = response.body()
                          byidfoodcategory.value = Resource.Success(mealResponse)
                      } else {
                          val errorMessage = response.message()
                          byidfoodcategory.value = Resource.Error(errorMessage)
                      }
                  } catch (t: Throwable) {
                      val errorMessage = t.message
                      byidfoodcategory.value = Resource.Error(errorMessage)
                  }
              }

    }
    private fun getCategories(){
        viewModelScope.launch {
            foodCategory.value = Resource.Loading()
            try {
                val response = withContext(Dispatchers.IO){
                    RetrofitInstrance.foodApi.getCategories().execute()
                }
                if(response.isSuccessful){
                    val categotyResponse = response.body()
                    foodCategory.value = Resource.Success(categotyResponse)
                }else{
                    val errorMessage = response.message()
                    foodCategory.value = Resource.Error(errorMessage)
                }
            }catch (t : Throwable){
                val errorMessage = t.message
                foodCategory.value = Resource.Error(errorMessage)
            }
        }

    }
     fun getMealByName(name : String){
        viewModelScope.launch {
            byidfoodcategory.value = Resource.Loading()
            try {
                val response = withContext(Dispatchers.IO){
                    RetrofitInstrance.foodApi.getMealByName(name).execute()
                }
                if(response.isSuccessful){
                    val mealResponse = response.body()
                    bynamefoodrandom.value = Resource.Success(mealResponse)
                }else{
                    val errorMessage = response.message()
                    byidfoodcategory.value = Resource.Error(errorMessage)
                }
            }catch (t : Throwable){
                val errorMessage = t.message
                bynamefoodrandom.value = Resource.Error(errorMessage)
            }
        }
    }
    fun observerFoodMeal(): LiveData<Resource<MealResponse>> {
        return foodMeal
    }
    fun observerFoodbyid(): LiveData<Resource<MealRandomResponse>>{
        return byidfoodcategory
    }
    fun observerRandomFood(): LiveData<Resource<MealRandomResponse>>{
        return randomfoodCategory
    }
    fun observerCategory(): LiveData<Resource<CategoryResponse>>{
        return foodCategory
    }
    fun observerCategotyList(): LiveData<Resource<MealResponse>>{
        return categoryList
    }
    fun observerFoodByName(): LiveData<Resource<MealRandomResponse>>{
        return bynamefoodrandom
    }
}