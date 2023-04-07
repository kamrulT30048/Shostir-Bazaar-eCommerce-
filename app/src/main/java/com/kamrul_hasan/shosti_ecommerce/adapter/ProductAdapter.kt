package com.kamrul_hasan.shosti_ecommerce.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.kamrul_hasan.shosti_ecommerce.R
import com.kamrul_hasan.shosti_ecommerce.model.product.ProductsItem
import com.kamrul_hasan.shosti_ecommerce.utils.MyApplication
import com.kamrul_hasan.shosti_ecommerce.utils.PRODUCT_KEY

class ProductAdapter(private val arrayList: List<ProductsItem>) : ArrayAdapter<ProductsItem>(
    MyApplication.appContext,
    R.layout.item_product, arrayList
) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(MyApplication.appContext)
        val view: View = inflater.inflate(R.layout.item_product, null)

        val productImage: ImageView = view.findViewById(R.id.iv_product_image)
        val productsName: TextView = view.findViewById(R.id.tv_product_name)

        Glide
            .with(MyApplication.appContext)
            .load(arrayList[position].image)
            .fitCenter()
            .into(productImage)

        productsName.text = arrayList[position].title

        view.setOnClickListener {
//            Toast.makeText(MyApplication.appContext, "item clicked", Toast.LENGTH_SHORT).show()

            val bundle = Bundle()
            bundle.putParcelable(PRODUCT_KEY, arrayList[position])
            view.findNavController().navigate(R.id.productDetailsFragment, bundle)
        }

        return view
    }

}