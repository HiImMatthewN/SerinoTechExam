package com.nantesmatthew.serinotechexam.products.presentation

import android.content.res.Resources
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.nantesmatthew.serinotechexam.core.utils.Status
import com.nantesmatthew.serinotechexam.databinding.FragmentProductDetailsBinding
import com.nantesmatthew.serinotechexam.products.domain.Product
import java.lang.Math.abs


const val PRODUCT_ID = "ProductId"

class ProductDetailsFragment : Fragment() {
    private lateinit var binder: FragmentProductDetailsBinding

    private lateinit var vpCarousel: ViewPager2
    private lateinit var btnBack: ImageButton


    private lateinit var tvCategory: TextView
    private lateinit var tvProductName: TextView
    private lateinit var tvDiscount: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvReviews:TextView
    private lateinit var tvProductInfo: TextView

    private lateinit var tvDiscountedPrice: TextView
    private lateinit var tvOriginalPrice: TextView
    private val productsViewModel by activityViewModels<ProductsViewModel>()

    companion object {
        private const val TAG = "ProductDetailsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack = binder.btnBack
        tvCategory = binder.tvCategory
        tvProductName = binder.tvProductName
        tvDiscount = binder.tvDiscount
        tvReviews = binder.tvReviews
        tvProductInfo = binder.tvProductInfo
        tvRating = binder.tvRating
        tvDiscountedPrice = binder.tvDiscountedPrice
        tvOriginalPrice = binder.tvOriginalPrice

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        arguments?.let { bundle ->
            val id = bundle.getInt(PRODUCT_ID, -1)
            productsViewModel.getProduct(id).observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val product = resource.data
                        if (product != null)
                            setProductToUI(product)
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADING -> {

                    }
                }
            }

        }
        vpCarousel = binder.viewPager
        vpCarousel.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((20 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }

        vpCarousel.setPageTransformer(compositePageTransformer)
    }

    private fun setProductToUI(product: Product) {
        vpCarousel.adapter = ProductImageAdapter(product.images)


        tvCategory.text = product.category
        tvProductName.text = product.title
        tvRating.text = product.rating.toString()
        tvProductInfo.text = product.description
        tvOriginalPrice.text = "$${product?.price}"
        tvDiscountedPrice.text = "$${product?.discountedPrice}"
        tvOriginalPrice.paintFlags =
            tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        val randomReviewCount = (0..999).random()
        tvReviews.text = "($randomReviewCount Reviews)"
    }
}