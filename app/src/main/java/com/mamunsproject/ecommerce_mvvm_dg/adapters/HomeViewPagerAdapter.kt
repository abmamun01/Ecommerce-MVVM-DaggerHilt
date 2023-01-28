package com.mamunsproject.ecommerce_mvvm_dg.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(
    private val fragment: List<Fragment>,
    fm: FragmentManager,
    lifcycle: Lifecycle
) : FragmentStateAdapter(fm, lifcycle) {
    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {

        return fragment[position]
    }
}