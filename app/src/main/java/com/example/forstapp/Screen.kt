package com.example.forstapp

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object SignatureScreen : Screen("signature_screen")
    object InputScreen : Screen("input_screen")
    object SurveyScreen : Screen("survey_screen")

}