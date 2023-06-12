package com.mamunsproject.ecommerce_mvvm_dg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.mamunsproject.ecommerce_mvvm_dg.data.Product
import com.mamunsproject.ecommerce_mvvm_dg.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainCategoryViewModel @Inject constructor(private val firestore: FirebaseFirestore) :
    ViewModel() {

    private val _specialProducts =
        MutableStateFlow<Resources<List<Product>>>(Resources.Unspecified())
    val specialProducts: StateFlow<Resources<List<Product>>> = _specialProducts

    private val _bestDealsProduct =
        MutableStateFlow<Resources<List<Product>>>(Resources.Unspecified())
    val bestDealsProduct: StateFlow<Resources<List<Product>>> = _bestDealsProduct

    private val _bestProducts =
        MutableStateFlow<Resources<List<Product>>>(Resources.Unspecified())
    val bestProducts: StateFlow<Resources<List<Product>>> = _bestProducts


    init {
        fetchSpecialProducts()
        fetchBestDeals()
        fetchBestProduct()
    }

    fun fetchSpecialProducts() {

        viewModelScope.launch {
            _specialProducts.emit(Resources.Loading())
        }

        firestore.collection("Products")
            .whereEqualTo("category", "Special Product")
            .get().addOnSuccessListener { result ->
                val specialProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _specialProducts.emit(Resources.Success(specialProductList))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resources.Error(it.message.toString()))
                }
            }
    }


    fun fetchBestDeals() {
        viewModelScope.launch {
            _bestDealsProduct.emit(Resources.Loading())
        }


        firestore.collection("Products")
            .whereEqualTo("category", "Best Deals")
            .get()
            .addOnSuccessListener { result ->
                val bestDealsProduct = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDealsProduct.emit(Resources.Success(bestDealsProduct))
                }

            }.addOnFailureListener {

                viewModelScope.launch {
                    _bestDealsProduct.emit(Resources.Error(it.message.toString()))
                }
            }


    }


    fun fetchBestProduct() {
        viewModelScope.launch {
            _bestProducts.emit(Resources.Loading())
        }


        firestore.collection("Products")
            .get()
            .addOnSuccessListener { result ->
                val bestProducts = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestProducts.emit(Resources.Success(bestProducts))
                }

            }.addOnFailureListener {

                viewModelScope.launch {
                    _bestProducts.emit(Resources.Error(it.message.toString()))
                }
            }

    }

}