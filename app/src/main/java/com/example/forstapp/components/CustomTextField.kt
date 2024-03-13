package com.example.forstapp.components

import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit = {},
    labelText: String,
    textStyle: TextStyle = LocalTextStyle.current,
    isValid: Boolean = true,
    invalidText: String = ""
){
    val isFocused = remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .onFocusChanged { isFocused.value = it.isFocused },
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = labelText,
                color = if (isFocused.value || value != "") {
                    Color(parseColor("#00BAFF"))
                } else {
                    Color(parseColor("#C1C1C1"))
                }
            )},
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )

    )
    HorizontalDivider(
        modifier = Modifier
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        thickness = 1.dp,
        color = Color(parseColor("#C1C1C1"))
    )
}
