package com.nantesmatthew.serinotechexam.products.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nantesmatthew.serinotechexam.R
import com.nantesmatthew.serinotechexam.databinding.ItemProductImageBinding

class ProductImageAdapter(private val imageUrls:List<String>):RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_image,parent,false)
        val binder = ItemProductImageBinding.bind(view)
        return ProductImageViewHolder(binder)
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.ivProductImage)


    }

    override fun getItemCount(): Int = imageUrls.size

    inner class ProductImageViewHolder(binder: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binder.root) {
        val ivProductImage = binder.ivProductImage


    }
}