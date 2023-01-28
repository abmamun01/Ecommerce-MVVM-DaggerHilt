package com.mamunsproject.ecommerce_mvvm_dg.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mamunsproject.ecommerce_mvvm_dg.R
import com.mamunsproject.ecommerce_mvvm_dg.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.shoppingHostFragment) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


    }
}