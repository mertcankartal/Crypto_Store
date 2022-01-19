package com.example.composecrypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composecrypto.ui.theme.ComposeCryptoTheme
import com.example.composecrypto.view.DetailScreen
import com.example.composecrypto.view.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCryptoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main_screen",
                ) {
                    composable("main_screen"){
                        MainScreen(navController = navController)
                    }

                    composable("detail_screen/{cryptoId}/{cryptoPrice}", arguments = listOf(
                        navArgument("cryptoId"){
                            type= NavType.StringType
                        },
                        navArgument("cryptoPrice"){
                            type= NavType.StringType
                        }
                    )){
                        val cryptoId=remember{
                            it.arguments?.getString("cryptoId")
                        }
                        val cryptoPrice = remember{
                            it.arguments?.getString("cryptoPrice")
                        }

                        DetailScreen(
                            id = cryptoId ?: "",
                            price = cryptoPrice ?: "",
                            navController = navController
                        )

                    }
                }
            }
        }
    }
}
