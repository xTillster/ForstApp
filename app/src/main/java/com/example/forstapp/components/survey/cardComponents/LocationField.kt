package com.example.forstapp.components.survey.cardComponents

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color.parseColor
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.Priority

private lateinit var fusedLocationClient: FusedLocationProviderClient

@Composable
fun LocationField(

){
    val context = LocalContext.current
    val active = remember { mutableStateOf(true) }
    val loadingLocation = remember { mutableStateOf(true) }
    val latitude = remember { mutableStateOf("") }
    val longitude = remember { mutableStateOf("") }


    Button(
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
        enabled = active.value,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(parseColor("#1E90FF")),
            contentColor = Color.White,
            disabledContainerColor = Color(parseColor("#1E90FF")),
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(25)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (loadingLocation.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .requiredSize(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Icon(
                    Icons.Rounded.MyLocation,
                    contentDescription = Icons.Rounded.MyLocation.name,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                //text = if (loadingLocation.value) "Standort abfragen..." else "Aktuellen Standort verwenden",
                text = "Standort ermitteln",
                fontWeight = FontWeight.Bold
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row {
        TextField(
            modifier = Modifier
                .weight(0.5f),
            value = longitude.value,
            onValueChange = { longitude.value = it })
        TextField(
            modifier = Modifier
                .weight(0.5f),
            value = latitude.value,
            onValueChange = { latitude.value = it })
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