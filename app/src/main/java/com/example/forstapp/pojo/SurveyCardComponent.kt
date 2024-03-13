package com.example.forstapp.pojo

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class SurveyCardComponent(
    //var surveyID: Int,    brauchen vermutlich nur die Composables
    //var surveyCardID: Int,
    var surveyComponentID: Int,
    var required: Boolean,
    var targetID: Int,
    var pageNumber: Int,
    var saveForNextAs: String,
    var usePreset: String,
    var matrixPosition: ArrayList<Int>,
    var label: String,
    var regexPattern: String
)
