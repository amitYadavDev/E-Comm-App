package com.amit.e_commerceapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amit.e_commerceapp.domain.model.CartItem


@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Int,
    val quantity: Int
)

fun CartItemEntity.toCartItem() = CartItem(productId, quantity)

fun CartItem.toCartItemEntity() = CartItemEntity(productId, quantity)
