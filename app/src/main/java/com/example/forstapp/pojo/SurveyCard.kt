package com.example.forstapp.pojo

import kotlinx.serialization.Serializable

@Serializable
data class SurveyCard(
    //var surveyID: Int,    brauchen vermutlich nur die Composables
    var surveyCardID: Int,
    var surveyTitle: String,
    var content: ArrayList<SurveyCardComponent>
)
