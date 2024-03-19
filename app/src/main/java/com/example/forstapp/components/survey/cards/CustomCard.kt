package com.example.forstapp.components.survey.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.forstapp.data.pojo.SurveyCard

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    surveyID: Int,
    //cardID: Int,
    surveyCard: SurveyCard
) {
    Column {
        Text(
            text = surveyCard.surveyCardTitle,
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 5.dp)
        )
        ElevatedCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(17.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color(android.graphics.Color.parseColor("#4F4F4F"))
            )
        ) {
            for (component in surveyCard.content){
                if (component.surveyComponentID == 0){
                    //CustomTextField(value = , onValueChange = , labelText = )
                }
            }
        }
    }
}
