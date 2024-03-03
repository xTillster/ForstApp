package com.example.forstapp.Components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.forstapp.POJO.ASP
import com.example.forstapp.Screen

@Composable
fun HomeScreen(navController: NavController){
    val context = LocalContext.current
    val user = remember { mutableStateOf("") }

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
                OutlinedCard(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(5.dp)
                        .clickable {
                            ASP.setEmptyASP()
                            navController.navigate(Screen.InputScreen.route)
                        }) {
                    Text(text = "ASP/KSP Meldung")
                }

                OutlinedCard(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp)) {
                    Text(text = "Abschußmeldung")
                }
            }
        }
    }
}