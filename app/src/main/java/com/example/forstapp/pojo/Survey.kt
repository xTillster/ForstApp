package com.example.forstapp.pojo

import kotlinx.serialization.Serializable

@Serializable
data class Survey(
    //Im Composable sollte eine Flag isFinished sein
    var surveyID: Int,
    var surveyName: String,
    var regions: ArrayList<String>,
    var content: ArrayList<SurveyCard>
){
    //constructor() : this(-1, "", ArrayList(), ArrayList())
}
