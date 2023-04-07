package com.kamrul_hasan.shosti_ecommerce.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kamrul_hasan.shosti_ecommerce.model.cart.CartProduct
import com.kamrul_hasan.shosti_ecommerce.model.user.User

@Dao
interface ShostirBazaarDao {

    // User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user_table WHERE userName = :userName")
    fun readUser(userName: String): LiveData<User>

    @Query("SELECT password FROM user_table WHERE userName = :userName")
    fun readUserPassword(userName: String): LiveData<String>

    @Query("UPDATE user_table SET imagePath = :uri  WHERE userName = :userName")
    suspend fun updateImage(userName: String, uri: String)

    @Query("UPDATE user_table SET fullName = :name WHERE userName = :userName")
    suspend fun updateFullName(userName: String, name: String)

    @Query("UPDATE user_table SET password = :password WHERE userName = :userName")
    suspend fun updatePassword(userName: String, password: String)

    // Cart List
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCartProduct(cartProduct: CartProduct)

    @Query("UPDATE cart_product SET amount = :amount WHERE id = :id")
    suspend fun updateCartProductAmount(id: Int, amount: Int)

    @Query("DELETE FROM cart_product WHERE id = :id")
    suspend fun deleteProductById(id: Int)

    @Query("DELETE FROM cart_product")
    suspend fun clearAllProducts()

    @Query("SELECT * FROM cart_product")
    fun readCartProducts(): LiveData<List<CartProduct>>

    @Query("SELECT * FROM cart_product WHERE id = :id")
    fun readCartProductById(id: Int): LiveData<CartProduct>

}