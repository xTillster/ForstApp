package com.example.forstapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.forstapp.Util.ASPDocumentBuilder
import com.example.forstapp.Util.BarcodeScanner
import com.example.forstapp.Util.Tools.Companion.convertLongToTime
import com.example.forstapp.ui.theme.ForstAppTheme
import kotlinx.coroutines.launch

class InputActivity : ComponentActivity(){

    lateinit var barcodeScanner: BarcodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            barcodeScanner = BarcodeScanner(context)
            ForstAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize()
                ) {
                    val barcodeResults = barcodeScanner.barCodeResults.collectAsStateWithLifecycle()

                    Column {
                        ScanBarcode(barcodeScanner::startScan, barcodeResults.value)
                        Button(onClick = {
                            ASPDocumentBuilder.setup(context)
                            ASPDocumentBuilder.createPdf()
                        }) {
                        Text(text = "PDF") }
                    }
                }
            }
        }
    }
}

@Composable
fun ScanBarcode(
    onScanBarcode: suspend () -> Unit,
    barcodeValue: String?
) {
    val scope = rememberCoroutineScope()

    Column {
        Button(onClick = {
            scope.launch {
                onScanBarcode()
            }
        }) {
            Text(text = "Profil")
        }

        BarcodeValueDisplay(barcodeValue)
        DatePickerView()
    }
}

@Composable
fun BarcodeValueDisplay(
    barcodeValue: String?
){
    Text(text = barcodeValue ?: "")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView() {

    val openDialog = remember { mutableStateOf(false) }
    //val dateResult = remember { mutableStateOf("Date Picker") } // = statt by
    val dateResult = remember { mutableStateOf(convertLongToTime(System.currentTimeMillis())) } // = statt by


    val datePickerState = rememberDatePickerState()
    val confirmEnabled =
        derivedStateOf { datePickerState.selectedDateMillis != null }


    if (openDialog.value) {

        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = { 
                TextButton(onClick = { openDialog.value = false
                var date = "Keine Auswahl"
                    if (datePickerState.selectedDateMillis!=null){
                        date = convertLongToTime(datePickerState.selectedDateMillis!!)
                    }
                    dateResult.value = date
                },
                    enabled = confirmEnabled.value
                ) {
                    Text(text = "Auswählen")
                }
            }) {
            DatePicker(
                state = datePickerState,
                title = { Text(text = "Datum") })
        }
    }

    OutlinedButton(
        onClick = { openDialog.value = true }) {
        Text(dateResult.value)
    }
}
