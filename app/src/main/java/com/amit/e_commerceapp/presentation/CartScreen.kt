package com.amit.e_commerceapp.presentation


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amit.e_commerceapp.domain.model.CartItem
import com.amit.e_commerceapp.domain.model.Product
import com.amit.e_commerceapp.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBack: () -> Unit,
    onPlaceOrder: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.placeOrder()
                onPlaceOrder()
            }) {
                Text("Place Order")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(uiState.value.cart) { cartItem ->
                CartItemView(cartItem, uiState.value.products)
            }
        }
    }
}

@Composable
fun CartItemView(cartItem: CartItem, products: List<Product>) {
    val product = products.find { it.id == cartItem.productId }
    Card {
        ListItem(
            headlineContent = { Text(product?.name ?: "Unknown") },
            supportingContent = { Text("Quantity: ${cartItem.quantity} | $${product?.price ?: 0.0}") }
        )
    }
}