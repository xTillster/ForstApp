package com.example.forstapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.forstapp.Util.ASPDocumentBuilder
import com.example.forstapp.Util.BarcodeScanner
import com.example.forstapp.Util.Tools.Companion.convertLongToTime
import com.example.forstapp.ui.theme.ForstAppTheme
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch


class InputActivity : ComponentActivity(){

    private lateinit var barcodeScanner: BarcodeScanner
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            barcodeScanner = BarcodeScanner(context)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            ForstAppTheme {

                var expanded1 by remember { mutableStateOf(false) }
                var expanded2 by remember { mutableStateOf(false) }
                var expanded3 by remember { mutableStateOf(false) }
                var expanded4 by remember { mutableStateOf(false) }
                var expanded5 by remember { mutableStateOf(false) }
                var expanded6 by remember { mutableStateOf(false) }


                val customerNumber = remember { mutableStateOf("") }
                val name = remember { mutableStateOf("") }
                val zipCode = remember { mutableStateOf("") }
                val city = remember { mutableStateOf("") }
                val streetNumber = remember { mutableStateOf("") }
                val phone = remember { mutableStateOf("") }
                val region = remember { mutableStateOf("Kreiszugehörigkeit") }
                val localZipCode = remember { mutableStateOf("") }
                val latitude = remember { mutableStateOf("") }
                val longitude = remember { mutableStateOf("") }
                val wildlifeOrigin = remember { mutableStateOf("") }
                val locationRegion = remember { mutableStateOf("Fundort ist ...") }
                val sex = remember { mutableStateOf("Geschlecht") }
                val trap = remember { mutableStateOf("Fallenfang") }
                val age = remember { mutableStateOf("Alter") }

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val barcodeResults = barcodeScanner.barCodeResults.collectAsStateWithLifecycle()

                    Column(
                        modifier = Modifier
                            //.background(Color.LightGray)
                            //.size(100.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        ScanBarcode(barcodeScanner::startScan, barcodeResults.value)
                        Button(onClick = {
                            ASPDocumentBuilder.setup(context)
                            ASPDocumentBuilder.createPdf()
                        }) {
                            Text(text = "PDF")
                        }
                        Text(text = "Angaben zum Probennehmer")
                        TextField(
                            value = customerNumber.value,
                            onValueChange = { customerNumber.value = it },
                            label = { Text("Kundennummer im LALLF") },
                            singleLine = true
                        )
                        TextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text("Name") },
                            singleLine = true
                        )
                        TextField(
                            value = zipCode.value,
                            onValueChange = { zipCode.value = it },
                            label = { Text("PLZ") },
                            singleLine = true
                        )
                        TextField(
                            value = city.value,
                            onValueChange = { city.value = it },
                            label = { Text("Ort") },
                            singleLine = true
                        )
                        TextField(
                            value = streetNumber.value,
                            onValueChange = { streetNumber.value = it },
                            label = { Text("Straße/Hausnummer") },
                            singleLine = true
                        )
                        TextField(
                            value = phone.value,
                            onValueChange = { phone.value = it },
                            label = { Text("Telefon") },
                            singleLine = true
                        )
                        Text(text = "Angaben zum Fundort")
                        OutlinedButton(onClick = { expanded1 = !expanded1 }) { //TODO vllt = true
                            Text(text = region.value)
                            DropdownMenu(
                                expanded = expanded1,
                                onDismissRequest = { expanded1 = false }) {
                                fun helper(name: String) {
                                    region.value = name
                                    expanded1 = false
                                }

                                DropdownMenuItem(
                                    text = { Text("LRO") },
                                    onClick = { helper("LRO") })
                                DropdownMenuItem(
                                    text = { Text("LUP") },
                                    onClick = { helper("LUP") })
                                DropdownMenuItem(
                                    text = { Text("MSE") },
                                    onClick = { helper("MSE") })
                                DropdownMenuItem(
                                    text = { Text("NWM") },
                                    onClick = { helper("NWM") })
                                DropdownMenuItem(text = { Text("VR") }, onClick = { helper("VR") })
                                DropdownMenuItem(text = { Text("VG") }, onClick = { helper("VG") })
                                DropdownMenuItem(
                                    text = { Text("HRO") },
                                    onClick = { helper("HRO") })
                                DropdownMenuItem(text = { Text("SN") }, onClick = { helper("SN") })
                            }
                        }
                        TextField(
                            value = localZipCode.value,
                            onValueChange = { localZipCode.value = it },
                            label = { Text("PLZ") },
                            singleLine = true
                        )

                        val active = remember { mutableStateOf(true) }
                        OutlinedButton(
                            onClick = { getLocation(context, latitude, active, longitude) },
                            enabled = active.value
                        ) {
                            Text(text = "Aktuellen Standort verwenden")
                        }
                        if (latitude.value != "" || longitude.value != "") {
                            Text(text = latitude.value)
                            Text(text = longitude.value)
                        }
                        OutlinedButton(onClick = { expanded2 = !expanded2 }) { //TODO vllt = true
                            Text(text = locationRegion.value)
                            DropdownMenu(
                                expanded = expanded2,
                                onDismissRequest = { expanded2 = false }) {
                                fun helper(name: String) {
                                    locationRegion.value = name
                                    expanded2 = false
                                }

                                DropdownMenuItem(
                                    text = { Text("nicht bekannt") },
                                    onClick = { helper("nicht bekannt") })
                                DropdownMenuItem(
                                    text = { Text("Kerngebiet") },
                                    onClick = { helper("Kerngebiet") })
                                DropdownMenuItem(
                                    text = { Text("kein Risikogebiet") },
                                    onClick = { helper("kein Risikogebiet") })
                                DropdownMenuItem(
                                    text = { Text("Sperrzone I Pufferzone") },
                                    onClick = { helper("Sperrzone I Pufferzone") })
                                DropdownMenuItem(
                                    text = { Text("Sperrzone II (gefährdetes Gebiet)") },
                                    onClick = { helper("Sperrzone II (gefährdetes Gebiet)") })
                            }
                        }
                        ElevatedCard {
                            Text(text = "Weitere Angaben")
                            Text(text = "Kennzeichen")
                        }
                        TextField(
                            value = wildlifeOrigin.value,
                            onValueChange = { wildlifeOrigin.value = it },
                            label = { Text("Wildursprungsscheinnr.") },
                            singleLine = true
                        )
                        DatePickerView()
                        OutlinedButton(onClick = { expanded3 = !expanded3 }) { //TODO vllt = true
                            Text(text = sex.value)
                            DropdownMenu(
                                expanded = expanded3,
                                onDismissRequest = { expanded3 = false }) {
                                fun helper(name: String) {
                                    sex.value = name
                                    expanded3 = false
                                }

                                DropdownMenuItem(
                                    text = { Text("männlich") },
                                    onClick = { helper("männlich") })
                                DropdownMenuItem(
                                    text = { Text("weiblich") },
                                    onClick = { helper("weiblich") })
                            }
                        }
                        OutlinedButton(onClick = { expanded4 = !expanded4 }) { //TODO vllt = true
                            Text(text = trap.value)
                            DropdownMenu(
                                expanded = expanded4,
                                onDismissRequest = { expanded4 = false }) {
                                fun helper(name: String) {
                                    trap.value = name
                                    expanded4 = false
                                }

                                DropdownMenuItem(
                                    text = { Text("ja") },
                                    onClick = { helper("ja") })
                                DropdownMenuItem(
                                    text = { Text("nein") },
                                    onClick = { helper("nein") })
                            }
                        }
                        OutlinedButton(onClick = { expanded5 = !expanded5 }) { //TODO vllt = true
                            Text(text = age.value)
                            DropdownMenu(
                                expanded = expanded5,
                                onDismissRequest = { expanded5 = false }) {
                                fun helper(name: String) {
                                    age.value = name
                                    expanded5 = false
                                }

                                DropdownMenuItem(
                                    text = { Text("0 - Frischling") },
                                    onClick = { helper("0 - Frischling") })
                                DropdownMenuItem(
                                    text = { Text("1 - Überläufer") },
                                    onClick = { helper("1 - Überläufer") })
                                DropdownMenuItem(
                                    text = { Text("2 - Adult") },
                                    onClick = { helper("2 - Adult") })
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getLocation(
        context: Context,
        latitude: MutableState<String>,
        active: MutableState<Boolean>,
        longitude: MutableState<String>
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            latitude.value = "Der GPS Verwendung muss zugestimmt werden"
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        } else {
            val tmp = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setGranularity(Granularity.GRANULARITY_FINE)
                .build()
            active.value = false
            latitude.value = "aktuellen Standort abrufen"
            longitude.value = ""
            fusedLocationClient.getCurrentLocation(tmp, null)
                .addOnSuccessListener { location: Location? ->

                    if (location != null) {
                        latitude.value = location.latitude.toString()
                        longitude.value = location.longitude.toString()
                    }
                }
            active.value = true
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
    val dateResult = remember { mutableStateOf(convertLongToTime(System.currentTimeMillis())) }


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
