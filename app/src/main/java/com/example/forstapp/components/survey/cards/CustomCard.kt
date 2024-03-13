package com.example.forstapp.components.survey.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    surveyID: Int,
    cardID: Int
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(17.dp),
        colors = CardDefaults.elevatedCardColors (
            containerColor = Color(android.graphics.Color.parseColor("#4F4F4F"))
        )
    ) {
    }
}
