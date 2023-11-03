package com.example.foodappretofit.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodappretofit.R
import com.example.foodappretofit.databinding.FragmentDetailesBinding
import com.example.foodappretofit.pojo.Meal
import com.example.foodappretofit.pojo.MealDB
import com.example.foodappretofit.pojo.MealList
import com.example.foodappretofit.resource.Resource
import com.example.foodappretofit.utils.Contract.MEAL
import com.example.foodappretofit.utils.Contract.MEAL_ID
import com.example.foodappretofit.utils.Contract.MEAL_LIST
import com.example.foodappretofit.viewmodel.FoodViewModel
import com.example.foodappretofit.viewmodel.FoodViewModelDao
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class DetailesFragment : Fragment() {
    private lateinit var binding: FragmentDetailesBinding
    private lateinit var viewModel : FoodViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailesBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this)[FoodViewModel::class.java]
        viewModelDao = ViewModelProviders.of(this)[FoodViewModelDao::class.java]
        val mealList = arguments?.getParcelable<MealList>(MEAL_LIST)
        val meal = arguments?.getParcelable<Meal>(MEAL)
        val strId = arguments?.getString(MEAL_ID)
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
        }
        if(meal != null){
            viewModel.getFoodbyid(meal.idMeal)
            this.idMeal = meal.idMeal
        }
        if (strId != null) {
            idMeal = strId
            viewModel.getFoodbyid(strId)

        }

        setFloatingButtonStatues()
        binding.imgbackDetail.setOnClickListener {
            findNavController().navigate(R.id.action_detailesFragment_to_homeFragment)
        }
        binding.imgYoutube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(urlYT)))
        }

        binding.btnSave.setOnClickListener {
            if(isMealSavedInDatabase()){
                deleteMealById()
                binding.btnSave.setImageResource(R.drawable.ic_baseline_save_24)

            }else{
                saveMealFavorite()
                binding.btnSave.setImageResource(R.drawable.ic_saved)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                viewModel.observerFoodbyid().observe(viewLifecycleOwner, { resources ->
                    lifecycleScope.launch {
                        when (resources) {
                            is Resource.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is Resource.Success -> {
                                var detail = resources.data?.meals
                                if (detail != null) {
                                    binding.progressBar.visibility = View.INVISIBLE
                                    innitView(detail[0])
                                    setInfomation()
                                }
                            }

                            is Resource.Error -> {
                                val errorMessage = resources.message
                            }
                        }
                    }

                })
    }

    private fun isMealSavedInDatabase():Boolean{
        return viewModelDao.isMealSavedInDatabase(idMeal.toInt())
    }
    private fun innitView(detailCategory: MealList) {
        this.mealList = detailCategory
        this.idMeal = detailCategory.idMeal
        this.strArea = detailCategory.strArea
        this.strMealThumb = detailCategory.strMealThumb
        this.strMeal = detailCategory.strMeal
        this.strYoutube = detailCategory.strYoutube
        this.strInstructions = detailCategory.strInstructions
        this.urlYT = detailCategory.strYoutube
        this.strCategory = detailCategory.strCategory
    }
    private fun deleteMealById() {
        viewModelDao.deleteMealById(idMeal.toInt())
    }

    private fun saveMealFavorite() {
        val meal = MealDB(idMeal.toInt(),
            strMeal,
            strArea,
            strCategory,
            strInstructions,
            strMealThumb,
            strYoutube)
        viewModelDao.insertMealFavorites(meal)
    }

    private fun setInfomation() {
        binding.apply {
            tvCategoryInfo.text = strCategory
            tvAreaInfo.text = strArea
            tvContent.text = strInstructions
            collapsingToolbar.title = strMeal
            Glide.with(requireContext())
                .load(strMealThumb)
                .into(imgMealDetail)
        }
    }
    private fun setFloatingButtonStatues() {
        if(isMealSavedInDatabase()){
            binding.btnSave.setImageResource(R.drawable.ic_saved)
        }else{
            binding.btnSave.setImageResource(R.drawable.ic_baseline_save_24)
        }
    }



}