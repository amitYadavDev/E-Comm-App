package com.amit.e_commerceapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amit.e_commerceapp.domain.model.CartItem
import com.amit.e_commerceapp.domain.model.Order
import com.amit.e_commerceapp.domain.model.Product
import com.amit.e_commerceapp.domain.usecase.ProductUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val cart: List<CartItem> = emptyList(),
    val selectedProduct: Product? = null
)


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val useCases: ProductUseCases): ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState get() = _uiState


    init {

        viewModelScope.launch(Dispatchers.IO) {
            useCases.getProducts().collectLatest { products ->
                _uiState.value = _uiState.value.copy(products = products)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            useCases.getCart().collectLatest { cart->
                _uiState.value = _uiState.value.copy(cart = cart)
            }
        }
    }

    fun selectProduct(id: Int) {
        viewModelScope.launch {
            useCases.getProductById(id).collectLatest { product ->
                _uiState.value = _uiState.value.copy(selectedProduct = product)
            }
        }
    }

    fun addToCart(productId: Int) {
        viewModelScope.launch {
            useCases.addToCard(CartItem(productId, 2))
        }
    }

    fun placeOrder() {
        viewModelScope.launch {
            val cart = _uiState.value.cart
            val total = _uiState.value.products.filter { p -> cart.any{it.productId == p.id} }
                .sumOf { it.price }
            useCases.placeOrder(Order(1, cart, total))
        }
    }

}