package com.example.forstapp.components.survey

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SurveyScreen(
    navController: NavController,
    surveyID: String?
) {
    var id = surveyID?.toInt() ?: -1
    var surveyName: String
    var regions: ArrayList<String>
}