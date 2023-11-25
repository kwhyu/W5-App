package com.kwhy.w5_app.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailWaifu : Screen("home/{waifuId}") {
        fun createRoute(waifuId: Int) = "home/$waifuId"
    }
}