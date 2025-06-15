package com.amit.e_commerceapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getCart(): Flow<List<CartItemEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(item: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun deleteAllCartItems()
}