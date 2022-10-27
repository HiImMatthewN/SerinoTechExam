package com.nantesmatthew.serinotechexam.products.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nantesmatthew.serinotechexam.products.domain.Product

@Entity(tableName = "product_table")
class ProductLocalEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "title")
    val title:String?,
    @ColumnInfo(name = "description")
    val description:String?,
    @ColumnInfo(name = "price")
    val price:Int?,
    @ColumnInfo(name = "discount_percentage")
    val discountPercentage:Double?,
    @ColumnInfo(name = "rating")
    val rating:Double?,
    @ColumnInfo(name = "stock")
    val stock:Int?,
    @ColumnInfo(name = "brand")
    val brand:String?,
    @ColumnInfo(name = "category")
    val category:String?,
    @ColumnInfo(name = "thumbnail")
    val thumbnail:String?,
    @ColumnInfo(name = "images")
    val images:List<String>?
)
fun ProductLocalEntity.toDomain(): Product {
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
        thumbNail = this.thumbnail ?: "",
        images = this.images ?: emptyList()

    )

}