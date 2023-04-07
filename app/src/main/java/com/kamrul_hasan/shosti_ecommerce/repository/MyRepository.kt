package com.kamrul_hasan.shosti_ecommerce.repository

import androidx.lifecycle.LiveData
import com.kamrul_hasan.shosti_ecommerce.database.ShostirBazaarDao
import com.kamrul_hasan.shosti_ecommerce.model.cart.CartProduct
import com.kamrul_hasan.shosti_ecommerce.model.user.User

class MyRepository(private val dao: ShostirBazaarDao) {

    fun readUser(userName: String): LiveData<User> {
        return dao.readUser(userName)
    }

    fun readUserPassword(userName: String): LiveData<String> {
        return dao.readUserPassword(userName)
    }

    suspend fun addUser(user: User) {
        dao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        dao.updateUser(user)
    }

    suspend fun updatePassword(userName: String, password: String) {
        dao.updatePassword(userName, password)
    }

    suspend fun updateFullName(userName: String, fullName: String) {
        dao.updateFullName(userName, fullName)
    }

    suspend fun updateImage(userName: String, imagePath: String) {
        dao.updateImage(userName, imagePath)
    }

    //  cart product

    fun readCartProduct(): LiveData<List<CartProduct>> = dao.readCartProducts()

    fun readCardProductById(id: Int): LiveData<CartProduct> {
        return dao.readCartProductById(id)
    }

    suspend fun addCartProduct(cartProduct: CartProduct) {
        dao.addCartProduct(cartProduct)
    }

    suspend fun deleteProductById(id: Int) {
        dao.deleteProductById(id)
    }

    suspend fun clearAllProduct() {
        dao.clearAllProducts()
    }

    suspend fun updateProductAmountById(id: Int, amount: Int) {
        dao.updateCartProductAmount(id, amount)
    }


}