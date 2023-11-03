package com.example.foodappretofit.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodappretofit.R
import com.example.foodappretofit.databinding.FragmentCategoriesBinding
import com.example.foodappretofit.databinding.FragmentDetailesBinding
import com.example.foodappretofit.pojo.MealDB
import com.example.foodappretofit.pojo.MealList
import com.example.foodappretofit.utils.Contract.MEAL_LIST
import com.example.foodappretofit.viewmodel.FoodViewModelDao

class DetailesActivity : AppCompatActivity() {

    private lateinit var binding : FragmentDetailesBinding
    private lateinit var viewModelDao: FoodViewModelDao
    private lateinit var mealList: MealList
    private var  idMeal = ""
    private var strArea = ""
    private var  strCategory = ""
    private var strInstructions = ""
    private var strMeal = ""
    private var strMealThumb = ""
    private var strYoutube = ""
    private var urlYT = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailesBinding.inflate(layoutInflater)
        val mealList = intent.getParcelableExtra<MealList>(MEAL_LIST)
        if(mealList != null){
            this.mealList = mealList
            this.idMeal = mealList.idMeal
            this.strArea = mealList.strArea
            this.strMealThumb = mealList.strMealThumb
            this.strMeal = mealList.strMeal
            this.strYoutube = mealList.strYoutube
            this.strInstructions = mealList.strInstructions
            this.urlYT = mealList.strYoutube
            this.strCategory = mealList.strCategory
            binding.progressBar.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.VISIBLE
        }
        binding.imgbackDetail.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.imgYoutube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlYT)))
        }
        innitView()
        setContentView(binding.root)

    }

    private fun innitView() {
        binding.apply {
            tvCategoryInfo.text = strCategory
            tvAreaInfo.text = strArea
            tvContent.text = strInstructions
            collapsingToolbar.title = strMeal
            Glide.with(this@DetailesActivity)
                .load(strMealThumb)
                .into(imgMealDetail)
        }
    }


}