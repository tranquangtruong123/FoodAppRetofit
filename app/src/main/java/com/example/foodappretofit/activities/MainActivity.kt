package com.example.foodappretofit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodappretofit.R
import com.example.foodappretofit.databinding.ActivityMainBinding
import com.example.foodappretofit.retrofit.FoodApi
import com.example.foodappretofit.retrofit.RetrofitInstrance

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottonNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailesFragment) {
                binding.bottonNavigation.visibility = View.GONE
            }
            else if (destination.id == R.id.categoryDetailesFragment) {
                binding.bottonNavigation.visibility = View.GONE
            }
            else {
                binding.bottonNavigation.visibility = View.VISIBLE
            }
        }
        setContentView(binding.root)

    }
}