package com.kamrul_hasan.shosti_ecommerce.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.databinding.FragmentProductDetailsBinding
import com.kamrul_hasan.shosti_ecommerce.model.cart.CartProduct
import com.kamrul_hasan.shosti_ecommerce.model.product.ProductsItem
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication
import com.kamrul_hasan.shosti_ecommerce.utils.PRODUCT_KEY
import com.kamrul_hasan.shosti_ecommerce.viewmodel.ShostiViewModel

class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShostiViewModel
    private lateinit var item: CartProduct

    private var productItem: ProductsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productItem = it.getParcelable(PRODUCT_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShostiViewModel::class.java]

        productItem?.let {
            binding.tvProductTitle.text = it.title
            binding.tvProductPrice.text = "$ ${it.price}"
            binding.tvProductRating.text = "Rating: ${it.rating.rate}/5.0"
            binding.tvProductSold.text = "Sold: ${it.rating.count}"
            binding.tvProductDescription.text = it.description

            Glide
                .with(MyApplication.appContext)
                .load(it.image)
                .fitCenter()
                .into(binding.ivProductImage)

            item = CartProduct(
                it.category,
                it.id,
                it.image,
                it.price,
                it.title,
                1
            )
        }

        binding.tvBuy.setOnClickListener {
            item.let {
                viewModel.addProduct(item)
            }
            findNavController().navigate(R.id.cartMenuFragment)
        }

        binding.tvAddCard.setOnClickListener {
            Toast.makeText(MyApplication.appContext, "One Item Added!!", Toast.LENGTH_SHORT).show()
            item.let {
                viewModel.addProduct(item)
            }
        }

    }
}