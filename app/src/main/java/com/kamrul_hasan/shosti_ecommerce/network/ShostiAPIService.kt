package com.kamrul_hasan.shosti_ecommerce.network

import com.kamrul_hasan.shosti_ecommerce.model.product.ProductsItem
import com.kamrul_hasan.shosti_ecommerce.utils.BASE_URL
import com.kamrul_hasan.shosti_ecommerce.utils.CATEGORY
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ShostiAPIService {

    @GET("products")
    fun getProducts(): Call<List<ProductsItem>>

    @GET("${CATEGORY}/{category_name}")
    fun getProductsByCategory(
        @Path("category_name") category_name: String
    ): Call<List<ProductsItem>>

}

object ShostiApi {
    val retrofitService: ShostiAPIService by lazy { retrofit.create(ShostiAPIService::class.java) }
}