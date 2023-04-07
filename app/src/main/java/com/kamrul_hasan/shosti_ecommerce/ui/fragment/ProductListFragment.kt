package com.kamrul_hasan.shosti_ecommerce.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kamrul_hasan.shosti_ecommerce.adapter.ProductAdapter
import com.kamrul_hasan.shosti_ecommerce.databinding.FragmentProductListBinding
import com.kamrul_hasan.shosti_ecommerce.model.product.ProductsItem
import com.kamrul_hasan.shosti_ecommerce.utils.CATEGORY_KEY
import com.kamrul_hasan.shosti_ecommerce.viewmodel.ShostiViewModel

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private var category: String = ""

    private var productList = listOf<ProductsItem>()
    private lateinit var viewModel: ShostiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(CATEGORY_KEY, "men's clothing")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShostiViewModel::class.java]

        viewModel.getProductsByCategory(category)
        viewModel.productsByCategory.observe(viewLifecycleOwner) {
            productList = it
            binding.lvProductList.adapter = ProductAdapter(it)
        }
        binding.lvProductList.isClickable = true
        /*binding.lvProductList.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(MyApplication.appContext, "from code", Toast.LENGTH_SHORT)
                .show()

            val bundle = Bundle()
            bundle.putParcelable(PRODUCT_KEY, productList[position])
//            findNavController().navigate(R.id.productDetailsFragment, bundle)
        }
        */

    }

}