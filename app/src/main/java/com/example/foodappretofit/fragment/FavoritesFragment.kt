package com.example.foodappretofit.fragment

import android.location.provider.ProviderProperties
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappretofit.R
import com.example.foodappretofit.adapter.FavoriteRecycleAdapter
import com.example.foodappretofit.databinding.FragmentFavoritesBinding
import com.example.foodappretofit.pojo.MealDB
import com.example.foodappretofit.resource.Resource
import com.example.foodappretofit.viewmodel.FoodViewModelDao
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FoodViewModelDao
    private lateinit var favoritesAdapter: FavoriteRecycleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[FoodViewModelDao::class.java]
        favoritesAdapter = FavoriteRecycleAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        FavoriteAdapter()
        return binding.root
    }

    private fun FavoriteAdapter() {
        binding.rcyFavorite.apply {
            adapter = favoritesAdapter
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeSavedAllFavorite().observe(viewLifecycleOwner,object: Observer<List<MealDB>>{
            override fun onChanged(t: List<MealDB>?) {
                if(t != null){
                    favoritesAdapter.differ.submitList(t)
                    binding.progressFav.visibility = View.GONE
                }else{
                    binding.progressFav.visibility = View.VISIBLE
                }
            }

        })
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favoriteMeal = favoritesAdapter.differ.currentList[position]
                viewModel.deleteFavoriteMeal(favoriteMeal)
                ShowDeleteSnackBar(favoriteMeal)
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rcyFavorite)
    }
    private fun ShowDeleteSnackBar(favoriteMeal: MealDB) {
        Snackbar.make(requireView(),"Meal was deletes",Snackbar.LENGTH_LONG).apply {
            setAction("Undo",View.OnClickListener {
                viewModel.insertMealFavorites(favoriteMeal)
            }).show()
        }
    }



}

