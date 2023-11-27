package com.nantesmatthew.serinotechexam.products.presentation

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.nantesmatthew.serinotechexam.products.domain.use_case.GetProductUseCase
import com.nantesmatthew.serinotechexam.products.domain.use_case.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TAG = "ProductsViewModel"
    }

    val productList = getProductsUseCase().asLiveData()



    fun getProduct(id: Int) = getProductUseCase(id).asLiveData(viewModelScope.coroutineContext)


}