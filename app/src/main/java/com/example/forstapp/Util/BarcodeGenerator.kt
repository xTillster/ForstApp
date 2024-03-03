package com.example.forstapp.Util

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

class BarcodeGenerator {

    companion object {
        private val size = 660
        private val size_width = 660
        private val size_height = 264
        var bitmap: Bitmap? = null

        @Throws(WriterException::class)
        fun createImage(message: String?, type: String?): Bitmap {
            var bitMatrix: BitMatrix? = null
            bitMatrix = when (type) {
                "QR Code" -> MultiFormatWriter().encode(
                    message,
                    BarcodeFormat.QR_CODE,
                    size,
                    size
                )

                "Barcode" -> MultiFormatWriter().encode(
                    message,
                    BarcodeFormat.CODE_128,
                    size_width,
                    size_height
                )

                "Data Matrix" -> MultiFormatWriter().encode(
                    message,
                    BarcodeFormat.DATA_MATRIX,
                    size,
                    size
                )

                "PDF 417" -> MultiFormatWriter().encode(
                    message,
                    BarcodeFormat.PDF_417,
                    size_width,
                    size_height
                )

                "Barcode-39" -> MultiFormatWriter().encode(
                    message,
                    BarcodeFormat.CODE_39,
                    size_width,
                    size_height
                )

                "Barcode-93" -> MultiFormatWriter().encode(
                    message,
                    BarcodeFormat.CODE_93,
                    size_width,
                    size_height
                )

                "AZTEC" -> MultiFormatWriter().encode(
                    message,
                    BarcodeFormat.AZTEC,
                    size,
                    size
                )

                else -> MultiFormatWriter().encode(
                    message,
                    BarcodeFormat.QR_CODE,
                    size,
                    size
                )
            }
            val width = bitMatrix.width
            val height = bitMatrix.height
            val pixels = IntArray(width * height)
            for (i in 0 until height) {
                for (j in 0 until width) {
                    if (bitMatrix[j, i]) {
                        pixels[i * width + j] = -0x1000000
                    } else {
                        pixels[i * width + j] = -0x1
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }
    }
}
