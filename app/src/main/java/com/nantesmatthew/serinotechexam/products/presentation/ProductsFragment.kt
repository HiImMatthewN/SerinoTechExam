package com.nantesmatthew.serinotechexam.products.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nantesmatthew.serinotechexam.R
import com.nantesmatthew.serinotechexam.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {
    private lateinit var binder: FragmentProductsBinding
    private lateinit var rvProducts: RecyclerView
    private val adapterProduct = ProductAdapter()
    private val productViewModel by activityViewModels<ProductsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentProductsBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvProducts = binder.rvProducts
        rvProducts.adapter = adapterProduct


        adapterProduct.onProductSelect = {
            val bundle = Bundle().apply { putInt(PRODUCT_ID,it.id) }
            findNavController().navigate(R.id.action_productsFragment_to_productDetailsFragment,bundle)
        }

        productViewModel.productList.observe(viewLifecycleOwner) {products->
            adapterProduct.submitList(products)
        }

    }
}