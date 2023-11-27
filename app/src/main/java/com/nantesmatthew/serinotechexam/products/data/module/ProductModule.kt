package com.nantesmatthew.serinotechexam.products.data.module

import com.nantesmatthew.serinotechexam.core.db.AppDatabase
import com.nantesmatthew.serinotechexam.products.data.data_source.ProductApi
import com.nantesmatthew.serinotechexam.products.data.data_source.ProductDao
import com.nantesmatthew.serinotechexam.products.data.repository.ProductRepositoryImpl
import com.nantesmatthew.serinotechexam.products.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Provides
    fun providesProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)


    @Provides
    fun providesProductDao(appDatabase: AppDatabase):ProductDao =
        appDatabase.productDao()


    @Provides
    fun providesProductRepository(api: ProductApi,dao:ProductDao):ProductRepository
     = ProductRepositoryImpl(api,dao)



}