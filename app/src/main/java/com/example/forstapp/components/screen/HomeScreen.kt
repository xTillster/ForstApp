package com.example.forstapp.components.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import androidx.navigation.NavController
import com.example.forstapp.data.pojo.ASP
import com.example.forstapp.Screen
import com.example.forstapp.data.serializer.SurveySerializer
import com.example.forstapp.util.JSONHandler

val Context.dataStore by dataStore("saved_surveys", SurveySerializer)

@Composable
fun HomeScreen(navController: NavController){
    val context = LocalContext.current
    val user = remember { mutableStateOf("") }
    val sharedPreferences = context.getSharedPreferences("fetchedSurveys", Context.MODE_PRIVATE)

    //TODO: only for debug
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
    println("size of sharedpreferences: " + sharedPreferences.all.size)

    val mapEntries = sharedPreferences.all

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column {
            Text(text = "Hallo")
            Text(text = user.value)
            Text(text = "Meldebereich")
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(5.dp)
                        .clickable {
                            ASP.setEmptyASP()
                            navController.navigate(Screen.InputScreen.route)
                        }) {
                    Text(text = "ASP/KSP Meldung")
                }

                ElevatedCard(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp)) {
                    Text(text = "Abschußmeldung")
                }


                mapEntries.entries.forEach { entry ->
                    val key = entry.key
                    //val value = entry.value as Survey
                    val handlerJSON = JSONHandler(context)
                    val value = handlerJSON.getData(key)

                    ElevatedCard(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(5.dp)
                            .clickable {
                                navController.navigate(Screen.SurveyScreen.route + "/" + value!!.surveyID)
                            }) {
                        Text(text = value!!.surveyName)
                    }
                }

            }
        }
    }

    fun initialize(){
    }
}
