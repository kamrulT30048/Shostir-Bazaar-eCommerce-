package com.kamrul_hasan.shosti_ecommerce.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kamrul_hasan.shosti_ecommerce.database.MyDatabase
import com.kamrul_hasan.shosti_ecommerce.model.cart.CartProduct
import com.kamrul_hasan.shosti_ecommerce.model.product.ProductsItem
import com.kamrul_hasan.shosti_ecommerce.model.user.User
import com.kamrul_hasan.shosti_ecommerce.network.ShostiApi
import com.kamrul_hasan.shosti_ecommerce.repository.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

private const val TAG = "ShostiViewModel"

class ShostiViewModel(application: Application) : AndroidViewModel(application) {

    private var _products: MutableLiveData<List<ProductsItem>> =
        MutableLiveData<List<ProductsItem>>()
    val products: LiveData<List<ProductsItem>> = _products
    private var _productsByCategory: MutableLiveData<List<ProductsItem>> =
        MutableLiveData<List<ProductsItem>>()
    val productsByCategory: LiveData<List<ProductsItem>> = _productsByCategory

    //    var _password: LiveData<String> =
    private val repository: MyRepository

    init {
        repository = MyRepository(
            MyDatabase.getDatabase(application)
                .ShostirBazaarDao()
        )

        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val products = ShostiApi.retrofitService.getProducts().await()
                Log.d(TAG, "getProducts:#size ${products.size}")
                Log.d(TAG, "getProducts:#list $products")
                _products.postValue(products)
            } catch (e: Exception) {
                Log.e(TAG, "getProducts: $e")
            }
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val products = ShostiApi.retrofitService.getProductsByCategory(category).await()
                Log.d(TAG, "getProductsByCategory:#size ${products.size}")
                _productsByCategory.postValue(products)
            } catch (e: Exception) {
                Log.e(TAG, "getProductsByCategory: $e")
            }
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addUser(user)
                Log.d(TAG, "addUser: add username \"${user.userName}\" successfully!!")
            } catch (e: Exception) {
                Log.e(TAG, "addUser: $e")
            }
        }
    }

    fun readUserPassword(userName: String): LiveData<String> {
        return repository.readUserPassword(userName)
    }

    fun addProduct(cartProduct: CartProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addCartProduct(cartProduct)
            } catch (e: Exception) {
                Log.e(TAG, "addProduct: $e")
            }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteProductById(id)
            } catch (e: Exception) {
                Log.e(TAG, "addProduct: $e")
            }
        }
    }

    fun readProduct(): LiveData<List<CartProduct>> {
        return repository.readCartProduct()
    }

    fun clearAllProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.clearAllProduct()
            } catch (e: Exception) {
                Log.e(TAG, "clearAllProduct: $e")
            }
        }
    }
}