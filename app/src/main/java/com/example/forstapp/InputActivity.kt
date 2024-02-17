package com.example.forstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.forstapp.Util.BarcodeScanner
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

                    ScanBarcode(barcodeScanner::startScan, barcodeResults.value)
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
    }
}

@Composable
fun BarcodeValueDisplay(
    barcodeValue: String?
){
    Text(text = barcodeValue ?: "")
}
