package com.amit.e_commerceapp.domain.usecase

import com.amit.e_commerceapp.domain.model.CartItem
import com.amit.e_commerceapp.domain.model.Order
import com.amit.e_commerceapp.domain.model.Product
import com.amit.e_commerceapp.domain.repos.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductUseCases @Inject constructor(
    private val repo: ProductRepository
) {
    fun getProducts(): Flow<List<Product>> = repo.getProducts()

    fun getProductById(id: Int): Flow<Product?> = repo.getProductById(id)

    suspend fun addToCard(cartItem: CartItem) = repo.addToCard(cartItem)

    fun getCart(): Flow<List<CartItem>> = repo.getCart()

    fun placeOrder(order: Order) = CoroutineScope(Dispatchers.IO).launch {
        repo.placeOrder(order)
    }

}