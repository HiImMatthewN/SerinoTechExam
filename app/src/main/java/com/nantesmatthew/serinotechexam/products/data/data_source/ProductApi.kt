package com.nantesmatthew.serinotechexam.products.data.data_source

import com.nantesmatthew.serinotechexam.products.data.data_source.reponse.ProductsResponse
import com.nantesmatthew.serinotechexam.products.data.entities.ProductNetworkEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun products(@Query("skip")skip:Int,@Query("limit") limit:Int):Response<ProductsResponse>

    @GET("products/{id}")
    suspend fun product(@Path("id")id:Int):Response<ProductNetworkEntity>

}