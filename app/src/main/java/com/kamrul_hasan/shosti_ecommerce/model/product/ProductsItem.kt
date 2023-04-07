package com.kamrul_hasan.shosti_ecommerce.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ProductsItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: @RawValue Rating,
    val title: String
    )
: Parcelable