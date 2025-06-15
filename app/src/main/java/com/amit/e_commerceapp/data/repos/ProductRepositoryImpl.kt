package com.amit.e_commerceapp.data.repos

import androidx.media3.common.util.Log
import com.amit.e_commerceapp.data.local.CartDao
import com.amit.e_commerceapp.data.local.ProductDao
import com.amit.e_commerceapp.data.local.toCartItem
import com.amit.e_commerceapp.data.local.toCartItemEntity
import com.amit.e_commerceapp.data.local.toEntity
import com.amit.e_commerceapp.data.local.toProduct
import com.amit.e_commerceapp.data.remote.ApiService
import com.amit.e_commerceapp.domain.model.CartItem
import com.amit.e_commerceapp.domain.model.Order
import com.amit.e_commerceapp.domain.model.Product
import com.amit.e_commerceapp.domain.repos.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao,
    private val cartDao: CartDao
): ProductRepository {
    override fun getProducts(): Flow<List<Product>> {
        return productDao.getAllProducts().map { entities->
            entities.map { it.toProduct() }
        }.also {
            syncProducts()
        }
    }

    override fun getProductById(id: Int): Flow<Product?> {
        return productDao.getProductById(id).map { it.toProduct() }
    }

    override suspend fun addToCard(cartItem: CartItem) {
        cartDao.addToCart(cartItem.toCartItemEntity())
    }

    override fun getCart(): Flow<List<CartItem>> {
        return cartDao.getCart().map { entities ->
            entities.map { it.toCartItem() }
        }
    }

    override suspend fun placeOrder(order: Order) {
        try {
            val response = apiService.placeOrder(order)
            if(response.isSuccessful) {
                cartDao.deleteAllCartItems()
            }
        } catch (e: Exception) {
            Log.e("place order ", "api is failing: $e")
            cartDao.deleteAllCartItems()
        }
    }

    private fun syncProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val products = apiService.getProducts()
                productDao.insert(products.map { it.toEntity() })
            } catch (e: Exception) {
                Log.e("syncProducts", "api is failing: $e")
                val sampleProducts = listOf(
                    Product(1, "Laptop", 999.99, "High-performance laptop with 16GB RAM", ""),
                    Product(2, "Smartphone", 699.99, "Latest smartphone with 128GB storage", ""),
                    Product(3, "Headphones", 149.99, "Wireless noise-cancelling headphones", ""),
                    Product(4, "Smartwatch", 199.99, "Fitness tracking smartwatch", ""),
                    Product(5, "Tablet", 349.99, "10-inch tablet with 64GB storage", ""))

                productDao.insert(sampleProducts.map { it.toEntity() })
            }
        }
    }
}