package com.amit.e_commerceapp.domain.repos

import com.amit.e_commerceapp.domain.model.CartItem
import com.amit.e_commerceapp.domain.model.Order
import com.amit.e_commerceapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>

    fun getProductById(id: Int): Flow<Product?>

   suspend fun addToCard(cartItem: CartItem)

    fun getCart(): Flow<List<CartItem>>

    suspend fun placeOrder(order: Order)

}