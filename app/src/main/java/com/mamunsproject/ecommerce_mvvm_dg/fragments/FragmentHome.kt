package com.mamunsproject.ecommerce_mvvm_dg.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.mamunsproject.ecommerce_mvvm_dg.R
import com.mamunsproject.ecommerce_mvvm_dg.adapters.HomeViewPagerAdapter
import com.mamunsproject.ecommerce_mvvm_dg.databinding.FragmentHomeBinding
import com.mamunsproject.ecommerce_mvvm_dg.fragments.categories.*


class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
            MainCategory(),
            ChairFragment(),
            CupBoardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )


        val viewPager2Adapter =
            HomeViewPagerAdapter(categoriesFragment, childFragmentManager, lifecycle)
        binding.viewPagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayoutID, binding.viewPagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Chair"
                2 -> tab.text = "Table"
                3 -> tab.text = "Accessory"
                4 -> tab.text = "Furniture"
            }
        }.attach()

    }


}