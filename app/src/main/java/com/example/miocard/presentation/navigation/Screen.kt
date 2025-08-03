package com.example.miocard.presentation.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object CreateCard : Screen("create_card")
    object EditCard : Screen("edit_card/{cardId}") {
        fun createRoute(cardId: String) = "edit_card/$cardId"
    }
}