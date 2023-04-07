package com.kamrul_hasan.shosti_ecommerce.model.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_product")
data class CartProduct(
    val category: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val price: Double,
    val title: String,
    val amount: Int
)
