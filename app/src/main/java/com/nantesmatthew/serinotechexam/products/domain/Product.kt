package com.nantesmatthew.serinotechexam.products.domain

import java.util.*


data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbNail: String,
    val images: List<String>
) {

    val discountedPrice: String
        get() =
            String.format(
                Locale.US,
                "%.2f",
                price - (price.toDouble() * (discountPercentage / 100))
            )


}