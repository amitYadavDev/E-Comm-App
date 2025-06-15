package com.amit.e_commerceapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amit.e_commerceapp.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onBack: () -> Unit,
    onAddToCart: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.selectProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addToCart(productId)
                onAddToCart()
            }) {
                Icon(Icons.Default.AddCircle, contentDescription = "Add to Cart")
            }
        }
    ) { padding ->
        uiState.value.selectedProduct?.let { product ->
            Column(modifier = Modifier.padding(padding)) {
                Text(product.name, style = MaterialTheme.typography.headlineMedium)
                Text("$${product.price}", style = MaterialTheme.typography.bodyLarge)
                Text(product.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}