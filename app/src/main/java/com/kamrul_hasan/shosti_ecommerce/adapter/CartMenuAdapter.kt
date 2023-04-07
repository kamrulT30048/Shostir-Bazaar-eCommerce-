package com.kamrul_hasan.shosti_ecommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.model.cart.CartProduct
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication
import com.kamrul_hasan.shosti_ecommerce.viewmodel.ShostiViewModel

class CartMenuAdapter(
    private val productList: List<CartProduct>,
    private val viewModel: ShostiViewModel
) : RecyclerView.Adapter<CartMenuAdapter.ProductHolder>() {

    class ProductHolder(binding: View) : RecyclerView.ViewHolder(binding.rootView) {
        val delete: ImageView = binding.findViewById(R.id.iv_cart_item_delete)
        val productImage: ImageView = binding.findViewById(R.id.iv_cart_item_product_image)
        val productTitle: TextView = binding.findViewById(R.id.tv_cart_item_product_title)
        val productPrice: TextView = binding.findViewById(R.id.tv_cart_item_product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(MyApplication.appContext)
            .inflate(R.layout.cart_menu_item, parent, false)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val productItem = productList[position]

        holder.productTitle.text = productItem.title
        val title = "$ ${productItem.price}"
        holder.productPrice.text = title

        Glide
            .with(MyApplication.appContext)
            .load(productItem.image)
            .fitCenter()
            .into(holder.productImage)

        holder.delete.setOnClickListener {
            // delete from cart list
            viewModel.deleteProduct(productItem.id)
            Toast.makeText(
                MyApplication.appContext,
                "One item removed!!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}