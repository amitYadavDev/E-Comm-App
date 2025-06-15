package com.amit.e_commerceapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amit.e_commerceapp.presentation.ui.theme.ECommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "product_list") {
        composable("product_list") {
            ProductListScreen(
                onProductClick = { id -> navController.navigate("product_detail/$id") },
                onCartClick = { navController.navigate("cart") }
            )
        }
        composable("product_detail/{productId}") { backStackEntry ->
            ProductDetailScreen(
                productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0,
                onBack = { navController.popBackStack() },
                onAddToCart = { navController.navigate("cart") }
            )
        }
        composable("cart") {
            CartScreen(
                onBack = { navController.popBackStack() },
                onPlaceOrder = { navController.popBackStack() }
            )
        }
    }
}