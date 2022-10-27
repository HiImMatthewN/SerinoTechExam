package com.nantesmatthew.serinotechexam.products.data.repository

import android.util.Log
import androidx.paging.*
import com.nantesmatthew.serinotechexam.core.utils.Resource
import com.nantesmatthew.serinotechexam.products.data.data_source.ProductApi
import com.nantesmatthew.serinotechexam.products.data.data_source.ProductDao
import com.nantesmatthew.serinotechexam.products.data.data_source.RemoteKeysDao
import com.nantesmatthew.serinotechexam.products.data.entities.toDomain
import com.nantesmatthew.serinotechexam.products.domain.Product
import com.nantesmatthew.serinotechexam.products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi, private val dao: ProductDao,
    private val remoteKeysDao: RemoteKeysDao
) : ProductRepository {

    companion object {
        private const val TAG = "ProductRepositoryImpl"
    }

    override fun products(): Flow<PagingData<Product>> {
        Log.d(TAG, "products: test")
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            remoteMediator = ProductRemoteMediator(
                api,
                remoteKeysDao,
                dao
            ),
            pagingSourceFactory = { dao.getProducts() }, config = PagingConfig(10)
        ).flow.map { it.map { it.toDomain() } }
    }

    override  fun product(id: Int): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.loading())
            val response = api.product(id)
            if (!response.isSuccessful) {
                emit(Resource.error("Error Occurred Fetching Product"))
            } else {
                val productEntity = response.body()
                val product = productEntity?.toDomain()
                emit(Resource.success(product))
            }

        } catch (e: Exception) {
            emit(Resource.error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}