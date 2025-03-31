package com.example.farmcab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.farmcab.MainScreen

import com.example.farmcab.ui.screens.LoginScreen
import com.example.farmcab.ui.screens.Signup
import com.example.farmcab.viewmodel.AuthViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("MainScreen") { MainScreen()}
        composable("signup") { Signup(navController, authViewModel) }
    }
}
