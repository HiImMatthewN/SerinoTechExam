package com.nantesmatthew.serinotechexam.products.domain.use_case

import com.nantesmatthew.serinotechexam.products.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: ProductRepository) {

     operator fun invoke(productId:Int) = repository.product(productId)


}