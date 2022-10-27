package com.nantesmatthew.serinotechexam.products.data.entities

import com.google.gson.annotations.SerializedName
import com.nantesmatthew.serinotechexam.products.domain.Product

class ProductNetworkEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: Int?,
    @SerializedName("discountPercentage") val discountPercentage: Double?,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("stock") val stock: Int?,
    @SerializedName("brand") val brand: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("thumbnail") val thumbNail: String?,
    @SerializedName("images") val images: List<String>?
)
fun ProductNetworkEntity.toDomain():Product{
    return Product(
        id = this.id,
        title = this.title ?: "Invalid Title",
        description = this.description ?: "Invalid Description",
        price = this.price ?: 0,
        discountPercentage = this.discountPercentage ?: 0.0,
        rating = this.rating ?: 0.0,
        stock = this.stock ?: 0,
        brand = this.brand ?: "Invalid Brand",
        category = this.category ?: "Invalid Category",
        thumbNail = this.thumbNail ?: "",
        images = this.images ?: emptyList()

    )

}
fun ProductNetworkEntity.toLocalEntity():ProductLocalEntity{
    return ProductLocalEntity(
        id = this.id,
        title = this.title ?: "Invalid Title",
        description = this.description ?: "Invalid Description",
        price = this.price ?: 0,
        discountPercentage = this.discountPercentage ?: 0.0,
        rating = this.rating ?: 0.0,
        stock = this.stock ?: 0,
        brand = this.brand ?: "Invalid Brand",
        category = this.category ?: "Invalid Category",
        thumbnail = this.thumbNail ?: "",
        images = this.images ?: emptyList()

    )

}