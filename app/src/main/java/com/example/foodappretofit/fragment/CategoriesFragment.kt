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
import com.example.foodappretofit.adapter.CategoryFoodRecycleAdapter
import com.example.foodappretofit.adapter.FavoriteRecycleAdapter
import com.example.foodappretofit.databinding.FragmentCategoriesBinding
import com.example.foodappretofit.resource.Resource
import com.example.foodappretofit.utils.Contract.CATEGORY
import com.example.foodappretofit.viewmodel.FoodViewModel
import kotlinx.coroutines.launch


class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryAdapter: CategoryFoodRecycleAdapter
    private lateinit var viewModel : FoodViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this)[FoodViewModel::class.java]
        categoryAdapter = CategoryFoodRecycleAdapter()
        CategoryAdapter()
        onCategoryClick()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observerCategory().observe(viewLifecycleOwner,{resources->
            lifecycleScope.launch {
                when(resources){
                    is Resource.Loading ->{
                        binding.progressCategory.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val categoryResponse = resources.data
                        if (categoryResponse != null) {
                            binding.progressCategory.visibility = View.INVISIBLE
                            val category = categoryResponse.categories
                            categoryAdapter.differ.submitList(category)
                        }
                    }
                    is Resource.Error -> {

                     }
                }
            }
        })
    }
    private fun onCategoryClick(){
        categoryAdapter.onItemClick = {category->
            val bundle = bundleOf(
               CATEGORY to category
            )
            findNavController().navigate(R.id.action_categoriesFragment_to_categoryDetailesFragment,bundle)
        }
    }
    private fun CategoryAdapter(){
        binding.rycCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        }
    }

}