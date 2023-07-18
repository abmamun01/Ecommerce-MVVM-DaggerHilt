package com.mamunsproject.ecommerce_mvvm_dg.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamunsproject.ecommerce_mvvm_dg.R
import com.mamunsproject.ecommerce_mvvm_dg.adapters.BestDealsAdapter
import com.mamunsproject.ecommerce_mvvm_dg.adapters.BestProductAdapter
import com.mamunsproject.ecommerce_mvvm_dg.adapters.SpecialProductAdapter
import com.mamunsproject.ecommerce_mvvm_dg.data.Product
import com.mamunsproject.ecommerce_mvvm_dg.databinding.FragmentMainCategoryBinding
import com.mamunsproject.ecommerce_mvvm_dg.utils.Resources
import com.mamunsproject.ecommerce_mvvm_dg.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private val TAG = "MainCategory"


@AndroidEntryPoint
class MainCategory : Fragment(R.layout.fragment_main_category) {
    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var specialProductAdapter: SpecialProductAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()

    private lateinit var bestDealsAdapter: BestDealsAdapter
    private lateinit var bestProductAdapter: BestProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpecialProductRv()
        setupBestDealsProductRV()
        setupBestProductsRV()


        // observe viewModel
        specialProductsFetcher()
        bestDealsProductsFetcher()
        bestProductsFetcher()


    }


    fun specialProductsFetcher() {
        lifecycleScope.launchWhenStarted {

            viewModel.specialProducts.collectLatest {
                when (it) {
                    is Resources.Loading -> {
                        showLoading()
                    }

                    is Resources.Success -> {
                        specialProductAdapter.differ.submitList(it.data)
                        hideLoading()

                    }

                    is Resources.Error -> {

                        hideLoading()
                        Log.e(TAG, "onViewCreated: ${it.message.toString()}")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit

                }
            }
        }

    }

    fun bestDealsProductsFetcher() {
        lifecycleScope.launchWhenStarted {

            viewModel.bestDealsProduct.collectLatest {
                when (it) {
                    is Resources.Loading -> {
                        showLoading()
                    }

                    is Resources.Success -> {
                        bestDealsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }

                    is Resources.Error -> {
                        hideLoading()
                        Log . e (TAG, "onViewCreated: ${it.message.toString()}")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit

                }
            }
        }
    }

    fun bestProductsFetcher() {
        lifecycleScope.launchWhenStarted {

            viewModel.bestProducts.collectLatest {
                when (it) {
                    is Resources.Loading -> {
                        binding.bestProductProgressBar.visibility = View.VISIBLE
                    }

                    is Resources.Success -> {
                        bestProductAdapter.differ.submitList(it.data)
                        binding.bestProductProgressBar.visibility = View.GONE
                    }

                    is Resources.Error -> {
                        binding.bestProductProgressBar.visibility = View.GONE
                        Log.e(TAG, "onViewCreated: ${it.message.toString()}")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit

                }
            }
        }

        binding.nestedScrollMainCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY) {
                viewModel.fetchBestProduct()
            }
        })
    }

    private fun setupBestProductsRV() {
        bestProductAdapter = BestProductAdapter()
        binding.rvBestProducts.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = bestProductAdapter
        }
    }

    private fun setupBestDealsProductRV() {
        bestDealsAdapter = BestDealsAdapter()
        binding.rvBestDealsProducts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = bestDealsAdapter
        }
    }

    private fun hideLoading() {

        binding.mainCategoryProgressBar.visibility = View.GONE

    }

    private fun showLoading() {

        binding.mainCategoryProgressBar.visibility = View.VISIBLE

    }

    private fun setupSpecialProductRv() {

        specialProductAdapter = SpecialProductAdapter()
        binding.rvSpecialProducts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = specialProductAdapter
        }
    }
}