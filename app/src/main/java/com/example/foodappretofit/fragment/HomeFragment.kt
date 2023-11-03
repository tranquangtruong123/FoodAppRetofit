package com.example.foodappretofit.fragment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodappretofit.R
import com.example.foodappretofit.adapter.CategoryFoodRecycleAdapter
import com.example.foodappretofit.adapter.SaleFoodRecycleAdapter
import com.example.foodappretofit.adapter.SearchFoodRecycleAdapter
import com.example.foodappretofit.databinding.FragmentHomeBinding
import com.example.foodappretofit.pojo.MealList
import com.example.foodappretofit.resource.Resource
import com.example.foodappretofit.utils.Contract.CATEGORY
import com.example.foodappretofit.utils.Contract.MEAL_ID
import com.example.foodappretofit.utils.Contract.MEAL_LIST
import com.example.foodappretofit.viewmodel.FoodViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var salefoodAdapter : SaleFoodRecycleAdapter
    private lateinit var categoryAdapter: CategoryFoodRecycleAdapter
    private lateinit var searchfoodAdapter: SearchFoodRecycleAdapter
    private  lateinit var viewModel: FoodViewModel
    private lateinit var  mealList: MealList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[FoodViewModel::class.java]
        salefoodAdapter = SaleFoodRecycleAdapter()
        categoryAdapter = CategoryFoodRecycleAdapter()
        searchfoodAdapter = SearchFoodRecycleAdapter()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        onSaleCategoryClick()
        onCategoryClick()
        SaleFoods()
        SearchFood()
        CategoryFood()
        onclickImage()
        onclickSearchItem()
        initListener()


        return binding.root
    }


    private fun initListener() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.isNotEmpty()){
                    binding.contraintLayout.visibility = View.GONE
                    binding.contraintLaouthint.visibility = View.VISIBLE
                }
                else{
                    binding.contraintLayout.visibility = View.VISIBLE
                    binding.contraintLaouthint.visibility = View.GONE
                }

            }
            override fun afterTextChanged(s: Editable) {
                var strKey = s.trim().toString()
                if(strKey.equals("") || strKey.length == 0){

                }else{
                    viewModel.getMealByName(strKey)
                }
            }
        })
        binding.imgSearch.setOnClickListener {
            var strKey = binding.edtSearch.text.trim().toString()
            if(strKey.equals("") || strKey.length == 0){

            }else{
                viewModel.getMealByName(strKey)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observerFoodMeal().observe(viewLifecycleOwner, { resource  ->
            lifecycleScope.launch {
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressbarHome.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val categoryResponse = resource.data
                        if (categoryResponse != null) {
                            binding.progressbarHome.visibility = View.INVISIBLE
                            val meals = categoryResponse.meals
                            salefoodAdapter.differ.submitList(meals)
                        }
                    }
                    is Resource.Error -> {
                        val errorMessage = resource.message
                    }
                }
            }
        })
        viewModel.observerRandomFood().observe(viewLifecycleOwner,{resources ->
            lifecycleScope.launch {
                when(resources){
                    is Resource.Loading -> {
                        binding.progressbarHome.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val meals = resources.data?.meals
                        if(meals != null){
                            binding.progressbarHome.visibility = View.INVISIBLE
                            setViewRandom(meals[0])
                        }
                    }
                    is Resource.Error -> {
                        val errorMessage = resources.message
                    }
                }
            }
        })
        viewModel.observerCategory().observe(viewLifecycleOwner,{resource->
            lifecycleScope.launch {
                when(resource){
                    is Resource.Loading -> {
                        binding.progressbarHome.visibility = View.INVISIBLE
                    }
                    is Resource.Success ->{
                        val categoryResponse = resource.data
                        if (categoryResponse != null) {
                            binding.progressbarHome.visibility = View.INVISIBLE
                            val category = categoryResponse.categories
                            categoryAdapter.differ.submitList(category)
                        }
                    }
                    is Resource.Error ->{
                        val errorMessage = resource.message
                    }
                }
            }
        })
        viewModel.observerFoodByName().observe(viewLifecycleOwner,{resource->
            lifecycleScope.launch {
                when(resource){
                    is Resource.Loading -> {
                        binding.progressbarHome.visibility = View.INVISIBLE
                    }
                    is Resource.Success ->{
                        val randomMealResponse = resource.data

                        if (randomMealResponse != null) {
                            binding.progressbarHome.visibility = View.INVISIBLE
                            val randomFood = randomMealResponse.meals
                            searchfoodAdapter.differ.submitList(randomFood)
                        }else{
                        }
                    }
                    is Resource.Error ->{
                        val errorMessage = resource.message
                    }
                }
            }
        })

    }

    private fun setNotiItem(checkstate: Boolean) {
        if(checkstate) binding.txtNotiitem.visibility = View.GONE
        else binding.txtNotiitem.visibility = View.VISIBLE
    }

    private fun onclickImage() {
        binding.homeImageview.setOnClickListener {
            val bottomDialogFragment = BottomDialogFragment()
            val mealList = mealList
            val bundle = bundleOf(MEAL_LIST to mealList)
            bottomDialogFragment.arguments = bundle
            bottomDialogFragment.show(childFragmentManager, "BottomSheetDialog")
        }
    }
    private fun onclickSearchItem() {
        searchfoodAdapter.onItemClick = { id->
            val bundle = bundleOf(
                MEAL_ID to id
            )
            findNavController().navigate(R.id.action_homeFragment_to_detailesFragment,bundle)
        }
    }

    private fun setViewRandom(mealList: MealList) {
        this.mealList = mealList
        binding.apply {
            Glide.with(requireContext())
                .load(mealList.strMealThumb)
                .into(binding.homeImageview)
        }
    }

    private fun onSaleCategoryClick(){
        salefoodAdapter.onItemClick ={ id ->
            val bundle = bundleOf(
                MEAL_ID to id
            )
            findNavController().navigate(R.id.action_homeFragment_to_detailesFragment,bundle)

        }
    }
    private fun onCategoryClick(){
        categoryAdapter.onItemClick = {category->
            val bundle = bundleOf(
                CATEGORY to category
            )
            findNavController().navigate(R.id.action_homeFragment_to_categoryDetailesFragment,bundle)
        }
    }
    private fun SaleFoods(){
        binding.rcySaleproduct.apply {
            adapter = salefoodAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }
    private fun CategoryFood() {
        binding.rycCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false)
        }
    }
    private fun SearchFood() {
        binding.rycSearch.apply {
            adapter = searchfoodAdapter
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        }
    }


}