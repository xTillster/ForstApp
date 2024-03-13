package com.example.forstapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.forstapp.components.HomeScreen
import com.example.forstapp.components.InputScreen
import com.example.forstapp.components.SignatureScreen
import com.example.forstapp.components.survey.SurveyScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.InputScreen.route) {
            InputScreen(navController = navController)
        }
        composable(route = Screen.SignatureScreen.route) {
            SignatureScreen(navController = navController)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.SurveyScreen.route + "/{surveyID}",
            arguments = listOf(navArgument("surveyID") { type = NavType.StringType })
        ) { backStackEntry ->
            SurveyScreen(
                navController = navController,
                backStackEntry.arguments?.getString("surveyID")
            )
        }
    }
}