package com.amit.e_commerceapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amit.e_commerceapp.domain.model.Product


@Entity(tableName = "products")
data class ProductEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val description: String,
    val image: String
)

fun ProductEntity.toProduct() = Product(id, name, price, description, image)

fun Product.toEntity() = ProductEntity(id, name, price, description, image)