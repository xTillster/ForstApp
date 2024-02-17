package com.example.forstapp.Util

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow

class BarcodeScanner(
    appContext: Context
) {

    val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_CODE_128
        )
        .enableAutoZoom()
        .build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    var barCodeResults = MutableStateFlow<String?>(null)

    suspend fun startScan(){
        try {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    barCodeResults.value = barcode.displayValue
                }
                .addOnCanceledListener {
                    barCodeResults.value = "failed"
                }
                .addOnFailureListener{e ->
                    barCodeResults.value = "failed"
                }
        } catch(e: Exception){

        }
    }
}