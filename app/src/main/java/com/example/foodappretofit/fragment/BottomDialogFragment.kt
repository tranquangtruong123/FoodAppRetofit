package com.example.foodappretofit.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodappretofit.R
import com.example.foodappretofit.activities.DetailesActivity
import com.example.foodappretofit.databinding.FragmentBottomDialogBinding
import com.example.foodappretofit.pojo.MealList
import com.example.foodappretofit.resource.Resource
import com.example.foodappretofit.utils.Contract
import com.example.foodappretofit.utils.Contract.MEAL_ID
import com.example.foodappretofit.utils.Contract.MEAL_LIST
import com.example.foodappretofit.viewmodel.FoodViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch


class BottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentBottomDialogBinding
    private lateinit var viewModel : FoodViewModel
    private lateinit var mealList: MealList
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomDialogBinding.inflate(inflater)
        val meallist = arguments?.getParcelable<MealList>(MEAL_LIST)
        if(meallist != null){
            this.mealList = meallist
            initView(mealList)
        }
        binding.contraintLayoutBottom.setOnClickListener {
            val intent = Intent(context, DetailesActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(MEAL_LIST, mealList)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme)
        viewModel = ViewModelProviders.of(this)[FoodViewModel::class.java]

        val strId = arguments?.getString(MEAL_ID)
        if (strId != null) {
            viewModel.getFoodbyid(strId)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initView(meals: MealList) {
        this.mealList = meals
        binding.apply {
            txtMealCategory.text = mealList.strCategory
            txtMealCountry.text = mealList.strArea
            txtReadMoreBtnsheet.text = mealList.strInstructions
            txtMealNameInBtmsheet.text = mealList.strMeal
            Glide.with(requireContext())
                .load(mealList.strMealThumb)
                .into(imgCategory)
        }
    }


}