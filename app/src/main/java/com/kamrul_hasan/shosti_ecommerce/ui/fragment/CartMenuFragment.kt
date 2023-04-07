package com.kamrul_hasan.shosti_ecommerce.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.adapter.CartMenuAdapter
import com.kamrul_hasan.shosti_ecommerce.databinding.FragmentCartMenuBinding
import com.kamrul_hasan.shosti_ecommerce.model.cart.CartProduct
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication
import com.kamrul_hasan.shosti_ecommerce.viewmodel.ShostiViewModel

class CartMenuFragment : Fragment() {
    private var _binding: FragmentCartMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShostiViewModel

    private var productList: List<CartProduct>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCartMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShostiViewModel::class.java]

        viewModel.readProduct().observe(viewLifecycleOwner) {
            productList = it
            binding.rvCartMenu.adapter = CartMenuAdapter(it, viewModel)

            var sum = 0.0
            it?.forEach { item ->
                sum += item.price
            }
            binding.tvCartSubTotalPrice.text =
                resources.getString(R.string.subtotal, sum.toString())
            sum += 20.0
            binding.tvCartTotalPrice.text = resources.getString(R.string.total, sum.toString())
        }
        val productsNumber = productList?.size ?: 0

        binding.tvCartConfirm.setOnClickListener {
            // oder confirmation
            Toast.makeText(
                MyApplication.appContext,
                "$productsNumber products oder confirm.",
                Toast.LENGTH_SHORT
            ).show()

            //remove product list
            viewModel.clearAllProduct()
        }

        binding.tvClearAll.setOnClickListener {
            viewModel.clearAllProduct()
            Toast.makeText(MyApplication.appContext, "Cart List Cleaned", Toast.LENGTH_SHORT).show()
        }

        binding.tvCartDeliveryCharge.text = resources.getString(R.string.delivery_charge, "20.0")
    }
}