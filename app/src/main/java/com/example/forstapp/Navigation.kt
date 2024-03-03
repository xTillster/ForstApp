package com.example.forstapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.forstapp.Components.HomeScreen
import com.example.forstapp.Components.InputScreen
import com.example.forstapp.Components.SignatureScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.InputScreen.route){
            InputScreen(navController = navController)
        }
        composable(route = Screen.SignatureScreen.route){
            SignatureScreen(navController = navController)
        }
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController)
        }
    }
}