package com.example.forstapp.components.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forstapp.components.survey.cards.CustomCard
import com.example.forstapp.util.JSONHandler

@Composable
fun SurveyScreen(
    navController: NavController,
    surveyID: String?
) {
    val json = JSONHandler(LocalContext.current)
    val survey = surveyID?.let { json.getData(it) }
    val viewModel = viewModel<SurveyViewModel>()

    if (survey == null) {
        Text(text = "Diese Umfrage existiert nicht")
        return
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Icon(
                Icons.Outlined.ArrowBackIosNew,
                contentDescription = Icons.Outlined.ArrowBackIosNew.name,
                modifier = Modifier
                    .size(20.dp)
                    .weight(0.2f)
            )
            Text(
                text = survey.surveyName,
                modifier = Modifier
                    .weight(0.6f)
            )
            Text(
                text = "Fertig",
                modifier = Modifier
                    .weight(0.2f)
                    .clickable {
                        //TODO: toggle for opening dynamic, probably with a queue for multiple elements
                    }
            )
        }
        /*LazyColumn() {
            items(survey.content) {
                if (it.surveyCardID == 0) {
                    CustomCard(surveyID = survey.surveyID, surveyCard = it)
                } else if (it.surveyCardID == 1) {
                    //TODO: single component Holder Card
                }
            }
        }*/
        Column {
            for (card in survey.content){
                CustomCard(surveyID = survey.surveyID, surveyCard = card)
            }
        }
    }
}