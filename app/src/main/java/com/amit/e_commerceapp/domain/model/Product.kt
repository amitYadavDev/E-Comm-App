package com.amit.e_commerceapp.domain.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val image: String,
)

data class CartItem(
    val productId: Int,
    val quantity: Int
)

data class Order(
    val id: Int,
    val items: List<CartItem>,
    val totalAmount: Double
)

