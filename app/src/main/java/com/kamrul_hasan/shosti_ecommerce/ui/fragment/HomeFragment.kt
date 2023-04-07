package com.kamrul_hasan.shosti_ecommerce.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.adapter.CategoryAdapter
import com.kamrul_hasan.shosti_ecommerce.databinding.FragmentHomeBinding
import com.kamrul_hasan.shosti_ecommerce.model.category.Category
import com.kamrul_hasan.shosti_ecommerce.model.product.ProductsItem
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication
import com.kamrul_hasan.shosti_ecommerce.viewmodel.ShostiViewModel

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private var _categoryList = mutableListOf<Category>()
    private var categoryList = listOf<Category>()

    private var _map: MutableMap<String, Int> = mutableMapOf()
    private var map: Map<String, Int> = _map

    private var productList = arrayListOf<ProductsItem>()
    private lateinit var viewModel: ShostiViewModel

    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShostiViewModel::class.java]

        showProgressBar()
        viewModel.products.observe(viewLifecycleOwner) {
            _map.clear()
            it.forEach { it1 ->
                val value = if (_map.containsKey(it1.category)) _map.getValue(it1.category) else 0
                _map.put(it1.category, value.plus(1))
            }
            Log.d(TAG, "onViewCreated: ${_map.size}")

            _categoryList.clear()
            _map.keys.forEach { it1 ->
                _categoryList.add(Category(it1, _map.getValue(it1)))
            }
            categoryList = _categoryList

            hideProgressBar()
            binding.gvCategory.layoutManager = GridLayoutManager(MyApplication.appContext, 2)
            binding.gvCategory.adapter = CategoryAdapter(categoryList)

        }
    }

    private fun showProgressBar() {

        dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_dialog)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }

}