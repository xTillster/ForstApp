package com.example.forstapp.Components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.forstapp.Navigation
import com.example.forstapp.POJO.ASP
import com.example.forstapp.POJO.Signature
import com.example.forstapp.Screen
import com.example.forstapp.Util.ASPDocumentBuilder
import com.example.forstapp.Util.BarcodeGenerator
import com.example.forstapp.Util.BarcodeScanner
import com.example.forstapp.Util.Tools.Companion.convertLongToTime
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch

private lateinit var barcodeScanner: BarcodeScanner
private lateinit var fusedLocationClient: FusedLocationProviderClient

/*class InputActivity {

    //private lateinit var barcodeScanner: BarcodeScanner
    //private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //InputScreen()
            /*
            val navController = rememberNavController()

            val signatureBitmap = remember { mutableStateOf<Bitmap?>(null) }

            val loadingLocation = remember { mutableStateOf(false) }

            var expanded1 by remember { mutableStateOf(false) }
            var expanded2 by remember { mutableStateOf(false) }
            var expanded3 by remember { mutableStateOf(false) }
            var expanded4 by remember { mutableStateOf(false) }
            var expanded5 by remember { mutableStateOf(false) }
            var expanded6 by remember { mutableStateOf(false) }
            var expanded7 by remember { mutableStateOf(false) }
            var expanded8 by remember { mutableStateOf(false) }

            val customerNumber = remember { mutableStateOf("") }
            val name = remember { mutableStateOf("") }
            val zipCode = remember { mutableStateOf("") }
            val city = remember { mutableStateOf("") }
            val streetNumber = remember { mutableStateOf("") }
            val phone = remember { mutableStateOf("") }
            val region = remember { mutableStateOf("Kreiszugehörigkeit") }
            val localZipCode = remember { mutableStateOf("") }
            val localCity = remember { mutableStateOf("") }
            val latitude = remember { mutableStateOf("") }
            val longitude = remember { mutableStateOf("") }
            val wildlifeOrigin = remember { mutableStateOf("") }
            val locationRegion = remember { mutableStateOf("Fundort ist ...") }
            val sex = remember { mutableStateOf("Geschlecht") }
            val trap = remember { mutableStateOf("Fallenfang") }
            val age = remember { mutableStateOf("Alter") }
            val material = remember { mutableStateOf("Untersuchungsmaterial") }
            val decomposition = remember { mutableStateOf("Verwesungsgrad des Tierkörpers") }
            val causeOfDeath = remember { mutableStateOf("Abgangsursache") }



            val context = LocalContext.current
            barcodeScanner = BarcodeScanner(context)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            ForstAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val barcodeResults = barcodeScanner.barCodeResults.collectAsStateWithLifecycle()
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ScanBarcode(barcodeScanner::startScan, barcodeResults.value)
                        Button(onClick = {
                            ASPDocumentBuilder.setup(context)
                            ASPDocumentBuilder.createPdf()
                        }) {
                            Text(text = "PDF")
                        }
                        ElevatedCard(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "Angaben zum Probennehmer")
                            OutlinedTextField(
                                value = customerNumber.value,
                                onValueChange = { customerNumber.value = it },
                                label = { Text("Kundennummer im LALLF") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                )
                            )
                            OutlinedTextField(
                                value = name.value,
                                onValueChange = { name.value = it },
                                label = { Text("Name") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                )
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier
                                        .weight(0.25f)
                                        .padding(8.dp),
                                    value = zipCode.value,
                                    onValueChange = { zipCode.value = it },
                                    label = { Text("PLZ") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Next
                                    )
                                )
                                OutlinedTextField(
                                    modifier = Modifier
                                        .weight(0.75f)
                                        .padding(8.dp),
                                    value = city.value,
                                    onValueChange = { city.value = it },
                                    label = { Text("Ort") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Next
                                    )
                                )
                            }

                            OutlinedTextField(
                                value = streetNumber.value,
                                onValueChange = { streetNumber.value = it },
                                label = { Text("Straße/Hausnummer") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                )
                            )

                            OutlinedTextField(
                                value = phone.value,
                                onValueChange = { phone.value = it },
                                label = { Text("Telefon") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                )
                            )
                        }
                        ElevatedCard {
                            Text(text = "Angaben zum Fundort")
                            OutlinedTextField(
                                value = localZipCode.value,
                                onValueChange = { localZipCode.value = it },
                                label = { Text("PLZ") },
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = localCity.value,
                                onValueChange = { localCity.value = it },
                                label = { Text("Ort") },
                                singleLine = true
                            )
                            OutlinedButton(onClick = {
                                expanded1 = !expanded1
                            }) { //TODO vllt = true
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
                                    DropdownMenuItem(
                                        text = { Text("VR") },
                                        onClick = { helper("VR") })
                                    DropdownMenuItem(
                                        text = { Text("VG") },
                                        onClick = { helper("VG") })
                                    DropdownMenuItem(
                                        text = { Text("HRO") },
                                        onClick = { helper("HRO") })
                                    DropdownMenuItem(
                                        text = { Text("SN") },
                                        onClick = { helper("SN") })
                                }
                            }
                        }
                        val active = remember { mutableStateOf(true) }
                        OutlinedButton(
                            modifier = Modifier
                                .width(300.dp),
                            onClick = {
                                getLocation(
                                    context,
                                    latitude,
                                    active,
                                    longitude,
                                    loadingLocation
                                )
                            },
                            enabled = active.value
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = if (loadingLocation.value) "Standort abfragen..." else "Aktuellen Standort verwenden",
                                    modifier = Modifier.weight(1f)
                                )

                                if (loadingLocation.value) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }
                            }
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
                        OutlinedTextField(
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
                        Image(
                            painter = BitmapPainter(
                                BarcodeGenerator.createImage(
                                    "ABC-123-Xcd",
                                    "Barcode"
                                ).asImageBitmap()
                            ), contentDescription = "Barcode"
                        )
                        OutlinedButton(onClick = { expanded6 = !expanded6 }) { //TODO vllt = true
                            Text(text = material.value)
                            DropdownMenu(
                                expanded = expanded6,
                                onDismissRequest = { expanded6 = false }) {
                                fun helper(name: String) {
                                    material.value = name
                                    expanded6 = false
                                }
                                DropdownMenuItem(
                                    text = { Text("Tierkörper") },
                                    onClick = { helper("Tierkörper") })
                                DropdownMenuItem(
                                    text = { Text("Bluttupfer") },
                                    onClick = { helper("Bluttupfer") })
                                DropdownMenuItem(
                                    text = { Text("Blut") },
                                    onClick = { helper("Blut") })
                                DropdownMenuItem(
                                    text = { Text("Tierkörperteile") },
                                    onClick = { helper("Tierkörperteile") })
                                DropdownMenuItem(
                                    text = { Text("Organe") },
                                    onClick = { helper("Organe") })
                            }
                        }
                        OutlinedButton(onClick = { expanded7 = !expanded7 }) { //TODO vllt = true
                            Text(text = decomposition.value)
                            DropdownMenu(
                                expanded = expanded7,
                                onDismissRequest = { expanded7 = false }) {
                                fun helper(name: String) {
                                    decomposition.value = name
                                    expanded7 = false
                                }
                                DropdownMenuItem(
                                    text = { Text("frisch") },
                                    onClick = { helper("frisch") })
                                DropdownMenuItem(
                                    text = { Text("leicht zersetzt") },
                                    onClick = { helper("leicht zersetzt") })
                                DropdownMenuItem(
                                    text = { Text("stark zersetzt") },
                                    onClick = { helper("stark zersetzt") })
                                DropdownMenuItem(
                                    text = { Text("Skelett mit Gewebe") },
                                    onClick = { helper("Skelett mit Gewebe") })
                                DropdownMenuItem(
                                    text = { Text("Skelett ohne Gewebe") },
                                    onClick = { helper("Skelett ohne Gewebe") })
                            }
                        }
                        OutlinedButton(onClick = { expanded8 = !expanded8 }) { //TODO vllt = true
                            Text(text = causeOfDeath.value)
                            DropdownMenu(
                                expanded = expanded8,
                                onDismissRequest = { expanded8 = false }) {
                                fun helper(name: String) {
                                    causeOfDeath.value = name
                                    expanded8 = false
                                }
                                DropdownMenuItem(
                                    text = { Text("gesund erlegt") },
                                    onClick = { helper("gesund erlegt") })
                                DropdownMenuItem(
                                    text = { Text("krank erlegt") },
                                    onClick = { helper("krank erlegt") })
                                DropdownMenuItem(
                                    text = { Text("verendet") },
                                    onClick = { helper("verendet") })
                                DropdownMenuItem(
                                    text = { Text("Unfallwild") },
                                    onClick = { helper("Unfallwild") })
                            }
                        }
                        OutlinedButton(onClick = { /*TODO*/ }) {
                            Text(text = "Unterschreiben")
                        }
                    }
                }
            }*/
        }
    }
    /*private fun getLocation(
        context: Context,
        latitude: MutableState<String>,
        active: MutableState<Boolean>,
        longitude: MutableState<String>,
        loading: MutableState<Boolean>
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
            loading.value = true
            latitude.value = ""
            longitude.value = ""
            fusedLocationClient.getCurrentLocation(tmp, null)
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        loading.value = false
                        latitude.value = location.latitude.toString()
                        longitude.value = location.longitude.toString()
                    }
                }.addOnFailureListener {
                    loading.value = false
                    latitude.value = "GPS nicht verfügbar"
                }
            active.value = true
        }
    }*/
}*/

@Composable
fun InputScreen(navController: NavController){
    val activity = (LocalContext.current as Activity)
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val loadingLocation = remember { mutableStateOf(false) }

    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }
    var expanded4 by remember { mutableStateOf(false) }
    var expanded5 by remember { mutableStateOf(false) }
    var expanded6 by remember { mutableStateOf(false) }
    var expanded7 by remember { mutableStateOf(false) }
    var expanded8 by remember { mutableStateOf(false) }

    val customerNumber = remember { mutableStateOf(ASP.currentASP.profile.customerNumber) }
    val name = remember { mutableStateOf(ASP.currentASP.profile.name) }
    val zipCode = remember { mutableStateOf(ASP.currentASP.profile.zipCode) }
    val city = remember { mutableStateOf(ASP.currentASP.profile.city) }
    val streetNumber = remember { mutableStateOf(ASP.currentASP.profile.streetNumber) }
    val phone = remember { mutableStateOf(ASP.currentASP.profile.phone) }
    val region = remember { mutableStateOf(ASP.currentASP.region) }
    val localZipCode = remember { mutableStateOf(ASP.currentASP.localZipCode) }
    val localCity = remember { mutableStateOf(ASP.currentASP.localCity) }
    val latitude = remember { mutableStateOf(ASP.currentASP.latitude) }
    val longitude = remember { mutableStateOf(ASP.currentASP.longitude) }
    val wildlifeOrigin = remember { mutableStateOf(ASP.currentASP.wildlifeOrigin) }
    val locationRegion = remember { mutableStateOf(ASP.currentASP.locationRegion) }
    val sex = remember { mutableStateOf(ASP.currentASP.sex) }
    val trap = remember { mutableStateOf(ASP.currentASP.trap) }
    val age = remember { mutableStateOf(ASP.currentASP.age) }
    val material = remember { mutableStateOf(ASP.currentASP.material) }
    val decomposition = remember { mutableStateOf(ASP.currentASP.decomposition) }
    val causeOfDeath = remember { mutableStateOf(ASP.currentASP.causeOfDeath) }

    val context = LocalContext.current
    barcodeScanner = BarcodeScanner(context)
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val barcodeResults = barcodeScanner.barCodeResults.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScanBarcode(barcodeScanner::startScan, barcodeResults.value)
            Button(onClick = {
                ASPDocumentBuilder.setup(context)
                ASPDocumentBuilder.createPdf()
            }) {
                Text(text = "PDF")
            }
            ElevatedCard(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Angaben zum Probennehmer")
                OutlinedTextField(
                    value = customerNumber.value,
                    onValueChange = { customerNumber.value = it },
                    label = { Text("Kundennummer im LALLF") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(0.25f)
                            .padding(8.dp),
                        value = zipCode.value,
                        onValueChange = { zipCode.value = it },
                        label = { Text("PLZ") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(0.75f)
                            .padding(8.dp),
                        value = city.value,
                        onValueChange = { city.value = it },
                        label = { Text("Ort") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        )
                    )
                }

                OutlinedTextField(
                    value = streetNumber.value,
                    onValueChange = { streetNumber.value = it },
                    label = { Text("Straße/Hausnummer") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value = phone.value,
                    onValueChange = { phone.value = it },
                    label = { Text("Telefon") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
            }
            ElevatedCard {
                Text(text = "Angaben zum Fundort")
                OutlinedTextField(
                    value = localZipCode.value,
                    onValueChange = { localZipCode.value = it },
                    label = { Text("PLZ") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = localCity.value,
                    onValueChange = { localCity.value = it },
                    label = { Text("Ort") },
                    singleLine = true
                )
                OutlinedButton(onClick = {
                    expanded1 = !expanded1
                }) { //TODO vllt = true
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
                        DropdownMenuItem(
                            text = { Text("VR") },
                            onClick = { helper("VR") })
                        DropdownMenuItem(
                            text = { Text("VG") },
                            onClick = { helper("VG") })
                        DropdownMenuItem(
                            text = { Text("HRO") },
                            onClick = { helper("HRO") })
                        DropdownMenuItem(
                            text = { Text("SN") },
                            onClick = { helper("SN") })
                    }
                }
            }
            val active = remember { mutableStateOf(true) }
            OutlinedButton(
                modifier = Modifier
                    .width(300.dp),
                onClick = {
                    getLocation(
                        context,
                        latitude,
                        active,
                        longitude,
                        loadingLocation
                    )
                },
                enabled = active.value
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (loadingLocation.value) "Standort abfragen..." else "Aktuellen Standort verwenden",
                        modifier = Modifier.weight(1f)
                    )

                    if (loadingLocation.value) {
                        Spacer(modifier = Modifier.width(8.dp))
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
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
            OutlinedTextField(
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
            Image(
                painter = BitmapPainter(
                    BarcodeGenerator.createImage(
                        "ABC-123-Xcd",
                        "Barcode"
                    ).asImageBitmap()
                ), contentDescription = "Barcode"
            )
            OutlinedButton(onClick = { expanded6 = !expanded6 }) { //TODO vllt = true
                Text(text = material.value)
                DropdownMenu(
                    expanded = expanded6,
                    onDismissRequest = { expanded6 = false }) {
                    fun helper(name: String) {
                        material.value = name
                        expanded6 = false
                    }
                    DropdownMenuItem(
                        text = { Text("Tierkörper") },
                        onClick = { helper("Tierkörper") })
                    DropdownMenuItem(
                        text = { Text("Bluttupfer") },
                        onClick = { helper("Bluttupfer") })
                    DropdownMenuItem(
                        text = { Text("Blut") },
                        onClick = { helper("Blut") })
                    DropdownMenuItem(
                        text = { Text("Tierkörperteile") },
                        onClick = { helper("Tierkörperteile") })
                    DropdownMenuItem(
                        text = { Text("Organe") },
                        onClick = { helper("Organe") })
                }
            }
            OutlinedButton(onClick = { expanded7 = !expanded7 }) { //TODO vllt = true
                Text(text = decomposition.value)
                DropdownMenu(
                    expanded = expanded7,
                    onDismissRequest = { expanded7 = false }) {
                    fun helper(name: String) {
                        decomposition.value = name
                        expanded7 = false
                    }
                    DropdownMenuItem(
                        text = { Text("frisch") },
                        onClick = { helper("frisch") })
                    DropdownMenuItem(
                        text = { Text("leicht zersetzt") },
                        onClick = { helper("leicht zersetzt") })
                    DropdownMenuItem(
                        text = { Text("stark zersetzt") },
                        onClick = { helper("stark zersetzt") })
                    DropdownMenuItem(
                        text = { Text("Skelett mit Gewebe") },
                        onClick = { helper("Skelett mit Gewebe") })
                    DropdownMenuItem(
                        text = { Text("Skelett ohne Gewebe") },
                        onClick = { helper("Skelett ohne Gewebe") })
                }
            }
            OutlinedButton(onClick = { expanded8 = !expanded8 }) { //TODO vllt = true
                Text(text = causeOfDeath.value)
                DropdownMenu(
                    expanded = expanded8,
                    onDismissRequest = { expanded8 = false }) {
                    fun helper(name: String) {
                        causeOfDeath.value = name
                        expanded8 = false
                    }
                    DropdownMenuItem(
                        text = { Text("gesund erlegt") },
                        onClick = { helper("gesund erlegt") })
                    DropdownMenuItem(
                        text = { Text("krank erlegt") },
                        onClick = { helper("krank erlegt") })
                    DropdownMenuItem(
                        text = { Text("verendet") },
                        onClick = { helper("verendet") })
                    DropdownMenuItem(
                        text = { Text("Unfallwild") },
                        onClick = { helper("Unfallwild") })
                }
            }
            fun saveState() {
                ASP.currentASP.profile.customerNumber = customerNumber.value
                ASP.currentASP.profile.name = name.value
                ASP.currentASP.profile.zipCode = zipCode.value
                ASP.currentASP.profile.city = city.value
                ASP.currentASP.profile.streetNumber = streetNumber.value
                ASP.currentASP.profile.phone = phone.value
                ASP.currentASP.region = region.value
                ASP.currentASP.localZipCode = localZipCode.value
                ASP.currentASP.localCity = localCity.value
                ASP.currentASP.latitude = latitude.value
                ASP.currentASP.longitude = longitude.value
                ASP.currentASP.wildlifeOrigin = wildlifeOrigin.value
                ASP.currentASP.locationRegion = locationRegion.value
                ASP.currentASP.sex = sex.value
                ASP.currentASP.trap = trap.value
                ASP.currentASP.age = age.value
                ASP.currentASP.material = material.value
                ASP.currentASP.decomposition = decomposition.value
                ASP.currentASP.causeOfDeath = causeOfDeath.value
            }
            OutlinedButton(onClick = {
                saveState()
                navController.navigate(Screen.SignatureScreen.route)
            }) {
                Text(text = "Unterschreiben")
            }
            Signature.signatureBitmap?.let {
                Image(
                    painter = BitmapPainter(
                        Signature.signatureBitmap!!.asImageBitmap()
                    ),
                    contentDescription = "Signature"
                )
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
) {
    Text(text = barcodeValue ?: "")
}

@SuppressLint("UnrememberedMutableState")
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
                TextButton(
                    onClick = {
                        openDialog.value = false
                        var date = "Keine Auswahl"
                        if (datePickerState.selectedDateMillis != null) {
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

private fun getLocation(
    context: Context,
    latitude: MutableState<String>,
    active: MutableState<Boolean>,
    longitude: MutableState<String>,
    loading: MutableState<Boolean>
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
        loading.value = true
        latitude.value = ""
        longitude.value = ""
        fusedLocationClient.getCurrentLocation(tmp, null)
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    loading.value = false
                    latitude.value = location.latitude.toString()
                    longitude.value = location.longitude.toString()
                }
            }.addOnFailureListener {
                loading.value = false
                latitude.value = "GPS nicht verfügbar"
            }
        active.value = true
    }
}

