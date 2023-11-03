package com.example.foodappretofit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodappretofit.R

import com.example.foodappretofit.adapter.CategoryListRecycleAdapter

import com.example.foodappretofit.databinding.FragmentCategoryDetailesBinding
import com.example.foodappretofit.pojo.Category
import com.example.foodappretofit.pojo.MealList

import com.example.foodappretofit.resource.Resource
import com.example.foodappretofit.utils.Contract.CATEGORY
import com.example.foodappretofit.utils.Contract.MEAL
import com.example.foodappretofit.viewmodel.FoodViewModel
import kotlinx.coroutines.launch


class CategoryDetailesFragment : Fragment() {
  private lateinit var binding : FragmentCategoryDetailesBinding
  private lateinit var viewModel: FoodViewModel
  private lateinit var categoryListAdapter: CategoryListRecycleAdapter
  private var  strCategory = ""
  private var countItem = 0;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryDetailesBinding.inflate(layoutInflater)
        binding.imgbackCtdetail.setOnClickListener {
            findNavController().navigate(R.id.action_categoryDetailesFragment_to_homeFragment)
        }
        DetailesCategory()
        onclickCategoryList()
        return binding.root
    }



    private fun initView() {
        binding.tvCategorytotal.text = strCategory + ": " + countItem
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observerCategotyList().observe(viewLifecycleOwner, { resource  ->
            lifecycleScope.launch {
                when (resource) {
                    is Resource.Loading -> {
                        binding.detaileProgress.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val categoryResponse = resource.data
                        if (categoryResponse != null) {
                            binding.detaileProgress.visibility = View.INVISIBLE
                            val meals = categoryResponse.meals
                            countItem = meals.size
                            initView()
                            categoryListAdapter.differ.submitList(meals)

                        }
                    }
                    is Resource.Error -> {
                        val errorMessage = resource.message
                    }
                }
            }
        })
    }
    private fun DetailesCategory() {
        binding.rcyCategorydetail.apply{
            adapter = categoryListAdapter
            layoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[FoodViewModel::class.java]
        categoryListAdapter = CategoryListRecycleAdapter()
        val categoriesList = arguments?.getParcelable<Category>(CATEGORY)
        if(categoriesList != null){
             strCategory = categoriesList.strCategory
            viewModel.getCategoryList(strCategory)
        }
    }
    private fun onclickCategoryList() {
        categoryListAdapter.onItemClick= { meal->
            val bundle = bundleOf(
                MEAL to meal
            )
            findNavController().navigate(R.id.action_categoryDetailesFragment_to_detailesFragment,bundle)
        }
        }



}