package com.nantesmatthew.serinotechexam.products.data.data_source

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nantesmatthew.serinotechexam.products.data.entities.ProductLocalEntity
import com.nantesmatthew.serinotechexam.products.domain.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(entities:List<ProductLocalEntity>):List<Long>

    @Query("SELECT * FROM product_table ORDER BY id ASC")
     fun getProducts(): PagingSource<Int, ProductLocalEntity>

     @Query("DELETE FROM product_table")
     suspend fun deleteAll()
}