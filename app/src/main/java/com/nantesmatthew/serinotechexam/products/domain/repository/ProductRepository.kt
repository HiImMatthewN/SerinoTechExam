package com.nantesmatthew.serinotechexam.products.domain.repository

import androidx.paging.PagingData
import com.nantesmatthew.serinotechexam.core.utils.Resource
import com.nantesmatthew.serinotechexam.products.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {


     fun products():Flow<PagingData<Product>>

     fun product(id:Int):Flow<Resource<Product>>


}