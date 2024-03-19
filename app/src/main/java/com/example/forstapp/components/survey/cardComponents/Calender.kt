package com.example.forstapp.components.survey.cardComponents

import android.annotation.SuppressLint
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.forstapp.util.Tools

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView() {
    val openDialog = remember { mutableStateOf(false) }
    //val dateResult = remember { mutableStateOf(convertLongToTime(System.currentTimeMillis())) }
    val dateResult = remember { mutableStateOf(Tools.convertLongToTime(System.currentTimeMillis())) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    val confirmEnabled =
        derivedStateOf { datePickerState.selectedDateMillis != null }


    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        var date = "Keine Auswahl"
                        if (datePickerState.selectedDateMillis != null) {
                            date = Tools.convertLongToTime(datePickerState.selectedDateMillis!!)
                        }
                        dateResult.value = date
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text(text = "Auswählen")
                }
            }) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    OutlinedButton(
        onClick = { openDialog.value = true }) {
        Text(dateResult.value)
    }
    /*if(openDialog.value){
        Box(){
            DatePicker(
                state = datePickerState,
                //title = { Text(text = "Datum") }
            )
        }
    }*/
}
