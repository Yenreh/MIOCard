package com.example.miocard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.miocard.presentation.screens.CreateCardScreen
import com.example.miocard.presentation.screens.EditCardScreen
import com.example.miocard.presentation.screens.MainScreen

@Composable
fun MIOCardNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToCreateCard = {
                    navController.navigate(Screen.CreateCard.route)
                },
                onNavigateToEditCard = { cardId ->
                    navController.navigate(Screen.EditCard.createRoute(cardId))
                }
            )
        }
        
        composable(Screen.CreateCard.route) {
            CreateCardScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.EditCard.route) { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId") ?: return@composable
            EditCardScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}