package com.example.forstapp.data.pojo

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.Serializable

@Serializable
data class Surveys(
    val surveys: PersistentList<Survey> = persistentListOf()
)

@Serializable
data class Survey(
    //Im Composable sollte eine Flag isFinished sein
    val surveyID: Int,
    val surveyName: String,
    val regions: PersistentList<String> = persistentListOf(),
    val content: PersistentList<SurveyCard> = persistentListOf()
)

@Serializable
data class SurveyCard(
    //var surveyID: Int,    brauchen vermutlich nur die Composables
    val surveyCardID: Int,
    val surveyCardTitle: String,
    val content: PersistentList<SurveyCardComponent> = persistentListOf(),
)

@Serializable
data class SurveyCardComponent(
    //var surveyID: Int,    brauchen vermutlich nur die Composables
    //var surveyCardID: Int,
    val surveyComponentID: Int,
    val required: Boolean,
    val targetID: Int,
    val pageNumber: Int,
    val saveForNextAs: String,
    val usePreset: String,
    //val matrixPosition: ArrayList<Int>,
    val matrixPosition: PersistentList<Int> = persistentListOf(),
    //val content: ArrayList<String>,
    val content: PersistentList<String> = persistentListOf(),
    val regexPattern: String
)

@Serializable
data class Answers(
    val answers: PersistentMap<Int, Map<Int, String>> = persistentMapOf()
)