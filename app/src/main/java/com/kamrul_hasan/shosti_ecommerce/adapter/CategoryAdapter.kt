package com.kamrul_hasan.shosti_ecommerce.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.model.category.Category
import com.kamrul_hasan.shosti_ecommerce.utils.CATEGORY_KEY
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication

class CategoryAdapter(
    private val categoryList: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    class CategoryHolder(binding: View) : RecyclerView.ViewHolder(binding.rootView) {
        val category: TextView = binding.findViewById(R.id.tv_category)
        val products: TextView = binding.findViewById(R.id.tv_product_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(MyApplication.appContext)
            .inflate(R.layout.item_category, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val categoryItem = categoryList[position]

        holder.category.text = categoryItem.categoryName
        holder.products.text = categoryItem.product.toString()

        holder.itemView.setOnClickListener {
            Toast.makeText(MyApplication.appContext, categoryItem.categoryName, Toast.LENGTH_SHORT)
                .show()

            val bundle = Bundle()
            bundle.putString(CATEGORY_KEY, categoryItem.categoryName)
            holder.itemView.findNavController().navigate(R.id.productListFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}