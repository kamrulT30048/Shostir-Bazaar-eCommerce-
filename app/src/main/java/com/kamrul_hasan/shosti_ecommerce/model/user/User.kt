package com.kamrul_hasan.shosti_ecommerce.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val userName: String,
    val fullName: String?,
    val email: String,
    val password: String,
    val imagePath: String?
)
