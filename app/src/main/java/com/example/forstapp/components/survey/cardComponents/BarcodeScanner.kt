package com.example.forstapp.components.survey.cardComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.forstapp.util.BarcodeScanner
import kotlinx.coroutines.launch

@Composable
fun ScanBarcode(
    //onScanBarcode: suspend () -> Unit,
    barcodeValue: String?,
    value: String
) {
    val scope = rememberCoroutineScope()
    val barcodeScanner = BarcodeScanner(LocalContext.current)


    Column {
        Button(onClick = {
            scope.launch {
                //onScanBarcode()
                barcodeScanner.startScan()
            }
        }) {
            Text(text = value)
        }
        BarcodeValueDisplay(barcodeValue)
    }
}

@Composable
fun BarcodeValueDisplay(
    barcodeValue: String?
) {
    Text(text = barcodeValue ?: "")
}