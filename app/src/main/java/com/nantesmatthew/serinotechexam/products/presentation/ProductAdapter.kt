package com.nantesmatthew.serinotechexam.products.presentation

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nantesmatthew.serinotechexam.R
import com.nantesmatthew.serinotechexam.databinding.ItemProductBinding
import com.nantesmatthew.serinotechexam.products.domain.Product

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(UIMODEL_COMPARATOR) {

    var onProductSelect: ((product: Product) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        val binder = ItemProductBinding.bind(view)
        return ProductViewHolder(binder)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        Glide.with(holder.itemView.context).load(product?.thumbNail).into(holder.ivThumbnail)
        holder.tvProductName.text = product?.title
        holder.tvDiscount.text = "- ${product?.discountPercentage}%"
        holder.tvOriginalPrice.text = "$${product?.price}"
        holder.tvDiscountedPrice.text = "$${product?.discountedPrice}"
        holder.tvCategory.text = product?.category
        holder.tvRating.text = product?.rating.toString()

        holder.tvOriginalPrice.paintFlags =
            holder.tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        if (product != null)
            holder.bind(product)
    }


    inner class ProductViewHolder(binder: ItemProductBinding) :
        RecyclerView.ViewHolder(binder.root) {
        val ivThumbnail = binder.ivThumbnail
        val tvProductName = binder.tvName
        val tvDiscount = binder.tvDiscount
        val tvDiscountedPrice = binder.tvDiscountedPrice

        val tvOriginalPrice = binder.tvOriginalPrice
        val tvCategory = binder.tvCategory
        val tvRating = binder.tvRating

        fun bind(product: Product){
            ivThumbnail.setOnClickListener {
                onProductSelect?.invoke(product)
            }
            itemView.setOnClickListener {
                onProductSelect?.invoke(product)
            }

        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}