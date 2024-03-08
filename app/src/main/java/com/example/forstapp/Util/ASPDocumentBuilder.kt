package com.example.forstapp.Util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.example.forstapp.POJO.ASP
import com.example.forstapp.POJO.ASPDocumentMap.Companion.printMap
import com.example.forstapp.R
import com.tom_roush.harmony.awt.geom.AffineTransform
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject
//import com.tom_roush.pdfbox.util.Matrix
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.Matrix


class ASPDocumentBuilder(){
    companion object {
        private lateinit var root : File
        private lateinit var assetManager : AssetManager
        private lateinit var appContext: Context
        private lateinit var asp: ASP

        /** Requieres Application context, Activity.getApplicationContext() **/
        fun setup(appContext: Context, asp: ASP){
            this.appContext = appContext
            PDFBoxResourceLoader.init(appContext)
            root = appContext.cacheDir
            assetManager = appContext.assets
            this.asp = asp
        }

        fun createPdf(){

            val sourceRawResourceId = R.raw.asp_pdf_blackwhite
            val destinationFileName = "destination.pdf" // Replace with the desired file name for the copied file in internal storage
            val copiedFile = copyFile(sourceRawResourceId, destinationFileName)

            //downloadFile(Uri.fromFile(copiedFile), "destination.pdf")

            val fileName = "destination.pdf" // Replace with the desired file name for the downloaded file

            //f values: Horizontal offset, positive moves text left, negative moves text right
            //e values: vertical offset, positive moves text upwards, negative moves text down

            val doc = PDDocument.load(copiedFile)
            val page = doc.getPage(0)
            val contentStream = PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true)
            contentStream.beginText()

            try {
                for(i in 1 .. 6){
                    printMap[i]?.let { it(contentStream, asp.profile.customerNumber[(-1+i)].toString()) }
                }
            } catch (ignored: Exception) {}

            printMap[7]?.let { it(contentStream, asp.profile.name) }
            printMap[8]?.let { it(contentStream, asp.profile.surname) }

            try {
                for(i in 9 .. 13){
                    printMap[i]?.let { it(contentStream, asp.profile.zipCode[(-9+i)].toString()) }
                }
            } catch (ignored: Exception) {}

            printMap[14]?.let { it(contentStream, asp.profile.city) }
            printMap[15]?.let { it(contentStream, asp.profile.streetNumber) }
            printMap[16]?.let { it(contentStream, asp.profile.phone) }

            //printMap[17]?.let { it(contentStream, "") }

            try {
                for(i in 18 .. 22){
                    printMap[i]?.let { it(contentStream, asp.localZipCode[(-18+i)].toString()) }
                }
            } catch (ignored: Exception) {}

            printMap[23]?.let { it(contentStream, asp.localCity) }
            printMap[24]?.let { it(contentStream, asp.hegering) }

            when(asp.region) {
                "LRO" -> printMap[25]?.let { it(contentStream, "x") }
                "VR" -> printMap[26]?.let { it(contentStream, "x") }
                "LUP" -> printMap[27]?.let { it(contentStream, "x") }
                "VG" -> printMap[28]?.let { it(contentStream, "x") }
                "MSE" -> printMap[29]?.let { it(contentStream, "x") }
                "HRO" -> printMap[30]?.let { it(contentStream, "x") }
                "NWM" -> printMap[31]?.let { it(contentStream, "x") }
                "SN" -> printMap[32]?.let { it(contentStream, "x") }
            }

            //TODO: Fall "kein Standort geliefert" Ausgabe handlen

            while(asp.latitude.length < 9){
                asp.latitude = asp.latitude + "0"
            }
            while(asp.longitude.length < 9){
                asp.longitude = asp.longitude + "0"
            }

            //38 ist nicht in der Map -> Fehler im annotierten Dokument
            //+2 um den Dezimalpunkt und erste Zahl im String zu skippen (in Vorgabe)
            for(i in (33..40).filter {it != 38}){
                if (i == 33) printMap[i]?.let { it(contentStream, asp.latitude[1].toString()) }
                else if (i > 38) printMap[i]?.let { it(contentStream, asp.latitude[(-32+i)].toString()) }
                else printMap[i]?.let { it(contentStream, asp.latitude[(-33+i+2)].toString()) }
            }
            for(i in 41..47){
                if (i == 41) printMap[i]?.let { it(contentStream, asp.latitude[1].toString()) }
                else printMap[i]?.let { it(contentStream, asp.longitude[(-41+i+2)].toString()) }
            }

            when(asp.locationRegion) {
                "Kerngebiet" -> printMap[48]?.let { it(contentStream, "x") }
                "Sperrzone II (gefährdetes G.)" -> printMap[49]?.let { it(contentStream, "x") }
                "Sperrzone I Pufferzone" -> printMap[50]?.let { it(contentStream, "x") }
                "kein Risikogebiet" -> printMap[51]?.let { it(contentStream, "x") }
            }

            try {
                for (i in 52..58) {
                    printMap[i]?.let { it(contentStream, asp.wildlifeOrigin[(-52 + i)].toString()) }
                }
            } catch (ignored: Exception) {}

            /*printMap[60]?.let { it(contentStream, "2") }
            printMap[61]?.let { it(contentStream, "8") }
            printMap[62]?.let { it(contentStream, "0") }
            printMap[63]?.let { it(contentStream, "1") }
            printMap[64]?.let { it(contentStream, "4") }
            printMap[65]?.let { it(contentStream, "9") }*/

            when(asp.trap){
                "ja" -> printMap[66]?.let { it(contentStream, "x") }
                "nein" -> printMap[67]?.let { it(contentStream, "x") }
            }
            when(asp.sex){
                "männlich" -> printMap[68]?.let { it(contentStream, "x") }
                "weiblich" -> printMap[69]?.let { it(contentStream, "x") }
            }
            when(asp.age){
                "0 - Frischling" -> printMap[70]?.let { it(contentStream, "x") }
                "1 - Überläufer" -> printMap[71]?.let { it(contentStream, "x") }
                "2 - Adult" -> printMap[72]?.let { it(contentStream, "x") }
            }
            when(asp.material){
                "Tierkörper" -> printMap[73]?.let { it(contentStream, "x") }
                "Bluttupfer" -> printMap[74]?.let { it(contentStream, "x") }
                "Blut" -> printMap[75]?.let { it(contentStream, "x") }
                "Tierkörperteile" -> printMap[76]?.let { it(contentStream, "x") }
                "Organe" -> printMap[77]?.let { it(contentStream, "x") }
            }
            when(asp.decomposition){
                "frisch" -> printMap[78]?.let { it(contentStream, "x") }
                "leicht zersetzt" -> printMap[79]?.let { it(contentStream, "x") }
                "stark zersetzt" -> printMap[80]?.let { it(contentStream, "x") }
                "Skelett mit Gewebe" -> printMap[81]?.let { it(contentStream, "x") }
                "Skelett ohne Gewebe" -> printMap[82]?.let { it(contentStream, "x") }
            }
            when(asp.causeOfDeath){
                "gesund erlegt" -> printMap[83]?.let { it(contentStream, "x") }
                "krank erlegt" -> printMap[84]?.let { it(contentStream, "x") }
                "verendet" -> printMap[85]?.let { it(contentStream, "x") }
                "Unfallwild" -> printMap[86]?.let { it(contentStream, "x") }
            }

            // printMap[87]?.let { it(contentStream, "x") } was ist mit Anlagen?
            printMap[88]?.let { it(contentStream, asp.additionalInfo) }


            contentStream.endText()

            if(asp.barcode != "null" && asp.barcode != ""){
                val matrix = Matrix()
                matrix.setRotate(90f)

                val barcodeBitmap = BarcodeGenerator.createImage(asp.barcode, "Barcode")
                val rotatedBitmap = Bitmap.createBitmap(barcodeBitmap, 0, 0, barcodeBitmap.width, barcodeBitmap.height, matrix, true)

                val pdBitmapImage = bitmapToPDImageXObject(doc, rotatedBitmap)
                contentStream.drawImage(pdBitmapImage, 386F, 90F, pdBitmapImage.width.toFloat(), pdBitmapImage.height.toFloat())
            }

            contentStream.close()
            doc.save(File(appContext.filesDir, destinationFileName))
            doc.close()

            /*val downloadedFile = copyFileToDownloads(copiedFile, fileName)
            if (downloadedFile != null) {
                // File copied to Downloads directory successfully
            } else {
                // Error occurred while copying the file
            }*/
        }

        private fun copyFile(rawResourceId: Int, destinationFileName: String): File? {
            val inputStream = appContext.resources.openRawResource(rawResourceId)
            val destinationFile = File(appContext.filesDir, destinationFileName)

            try {
                FileOutputStream(destinationFile).use { outputStream ->
                    inputStream.use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                return destinationFile
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            } finally {
                inputStream.close()
            }
        }

        private fun openPdfWithExternalViewer(fileUri: Uri) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(fileUri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant read permission to the receiving app
            if (intent.resolveActivity(appContext.packageManager) != null) {
                appContext.startActivity(intent)
            } else {
                // Handle scenario where no PDF viewer app is installed
                Toast.makeText(appContext, "No PDF viewer application found", Toast.LENGTH_SHORT).show()
            }
        }

        private fun downloadFile(uri: Uri, fileName: String) {
            val downloadManager = appContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(uri)

            // Set the download file destination
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

            // Set the download description (optional)
            request.setDescription("Downloading PDF file")

            // Enqueue the download and retrieve the download ID
            val downloadId = downloadManager.enqueue(request)
        }

        private fun copyFileToDownloads(sourceFile: File?, fileName: String): File? {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val destinationFile = File(downloadsDir, fileName)

            try {
                FileInputStream(sourceFile).use { input ->
                    FileOutputStream(destinationFile).use { output ->
                        input.copyTo(output)
                    }
                }
                return destinationFile
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        private fun bitmapToPDImageXObject(document: PDDocument, bitmap: Bitmap): PDImageXObject {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val inputStream = ByteArrayInputStream(outputStream.toByteArray())
            return PDImageXObject.createFromByteArray(document, inputStream.readBytes(), "image")
        }

    }

}
