package com.nantesmatthew.serinotechexam.products.data.data_source.reponse

import com.google.gson.annotations.SerializedName
import com.nantesmatthew.serinotechexam.products.data.entities.ProductNetworkEntity

class ProductsResponse(
    @SerializedName("products") val products: List<ProductNetworkEntity>?,
    @SerializedName("total") val total: Int?,
    @SerializedName("skip") val skip: Int?,
    @SerializedName("limit") val limit: Int?
)