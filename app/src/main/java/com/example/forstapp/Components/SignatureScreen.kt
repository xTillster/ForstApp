package com.example.forstapp.Components

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.forstapp.POJO.Signature

@Composable
fun SignatureScreen(navController: NavController) {
    val canvasBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val lines = remember { mutableStateListOf<Line>() }
    val points = remember { mutableStateListOf<Offset>() }

    val context = LocalContext.current
    val activity = (LocalContext.current as Activity)
    val density = LocalDensity.current.density

    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    Column {
        Canvas(modifier = Modifier
            .width(500.dp)
            .height(300.dp)
            .border(1.dp, Color.Black)
            .clipToBounds()
            .pointerInput(true) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    val line = Line(
                        start = change.position - dragAmount,
                        end = change.position
                    )

                    lines.add(line)
                }
                detectTapGestures(
                    onPress = {
                        Toast.makeText(context, "press", Toast.LENGTH_LONG).show()
                    },
                    onDoubleTap = {
                        Toast.makeText(context, "double", Toast.LENGTH_LONG).show()
                    },
                    onLongPress = {
                        Toast.makeText(context, "long", Toast.LENGTH_LONG).show()
                    },
                    onTap = {
                        Toast.makeText(context, "tap", Toast.LENGTH_LONG).show()
                    }
                )
            }
        ) {
            lines.forEach { line ->
                drawLine(
                    color = line.color,
                    start = line.start,
                    end = line.end,
                    strokeWidth = line.strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                canvasBitmap.value = saveCanvasToBitmap(lines, points,(500.dp * density).value, (300.dp * density).value)
                Signature.signatureBitmap = canvasBitmap.value
                navController.navigateUp()
                //navController.navigate(Screen.InputScreen.route)
                //navController.popBackStack()
            }
        ) {
            Text("Save Canvas")
        }
    }
}

private fun saveCanvasToBitmap(lines: List<Line>, points: List<Offset>, width: Float, height: Float): Bitmap {
    val bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawCanvasContent(canvas, lines, points)

    return bitmap
}

private fun drawCanvasContent(canvas: Canvas, lines: List<Line>, points: List<Offset>) {
    val paint = Paint().apply {
        color = Color.Black.toArgb()
        strokeWidth = 4f
    }
    lines.forEach { line ->
        canvas.drawLine(
            line.start.x,
            line.start.y,
            line.end.x,
            line.end.y,
            paint
        )
    }

    points.forEach { point ->
        canvas.drawPoint(point.x, point.y, paint)
    }
}

@Immutable
data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 1.dp
)