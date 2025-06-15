package com.amit.e_commerceapp.data.remote

import com.amit.e_commerceapp.domain.model.Order
import com.amit.e_commerceapp.domain.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("/products")
    suspend fun getProducts(): List<Product>

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @POST("orders")
    suspend fun placeOrder(@Body order: Order): Response<Unit>
}