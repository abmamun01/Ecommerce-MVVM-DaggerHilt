package com.mamunsproject.ecommerce_mvvm_dg.data

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Float,
    val offerPercentage: Float? = null,
    val description: String? = null,
    val colors: List<Int>? = null,
    val sizes: List<String>? = null,
    val images: List<String>
) {
    constructor() : this("1", "", "", 0f,  images = emptyList())
}