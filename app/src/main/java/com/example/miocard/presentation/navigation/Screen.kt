package com.example.miocard.presentation.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object CreateCard : Screen("create_card")
}