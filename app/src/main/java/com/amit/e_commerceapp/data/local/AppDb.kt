package com.amit.e_commerceapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ProductEntity::class, CartItemEntity::class], version = 1)
abstract class AppDb: RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun cardDao(): CartDao
}