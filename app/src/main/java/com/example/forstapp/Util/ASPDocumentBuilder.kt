package com.example.forstapp.Util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.example.forstapp.POJO.ASPDocumentMap.Companion.printMap
import com.example.forstapp.R
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.font.PDType0Font
import com.tom_roush.pdfbox.pdmodel.font.PDType1CFont
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font
import com.tom_roush.pdfbox.util.Matrix
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class ASPDocumentBuilder(){
    companion object {
        private lateinit var root : File
        private lateinit var assetManager : AssetManager
        private lateinit var appContext: Context

        /** Requieres Application context, Activity.getApplicationContext() **/
        fun setup(appContext: Context){
            this.appContext = appContext
            PDFBoxResourceLoader.init(appContext)
            root = appContext.cacheDir
            assetManager = appContext.assets
        }

        fun createPdf(){
            val sourceRawResourceId = R.raw.asp_pdf
            val destinationFileName = "destination.pdf" // Replace with the desired file name for the copied file in internal storage
            val copiedFile = copyFile(sourceRawResourceId, destinationFileName)

            //openPdfWithExternalViewer(Uri.fromFile(copiedFile))

            //downloadFile(Uri.fromFile(copiedFile), "destination.pdf")

            val fileName = "destination.pdf" // Replace with the desired file name for the downloaded file

            //f values: Horizontal offset, positive moves text left, negative moves text right
            //e values: vertical offset, positive moves text upwards, negative moves text down

            val doc = PDDocument.load(copiedFile)
            val page = doc.getPage(0)
            val contentStream = PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true)
            contentStream.beginText()

            printMap[48]?.let { it(contentStream, "x") }
            printMap[49]?.let { it(contentStream, "x") }
            printMap[50]?.let { it(contentStream, "x") }
            printMap[51]?.let { it(contentStream, "x") }

            printMap[52]?.let { it(contentStream, "2") }
            printMap[53]?.let { it(contentStream, "8") }
            printMap[54]?.let { it(contentStream, "0") }
            printMap[55]?.let { it(contentStream, "1") }
            printMap[56]?.let { it(contentStream, "4") }
            printMap[57]?.let { it(contentStream, "1") }
            printMap[58]?.let { it(contentStream, "4") }

            printMap[60]?.let { it(contentStream, "2") }
            printMap[61]?.let { it(contentStream, "8") }
            printMap[62]?.let { it(contentStream, "0") }
            printMap[63]?.let { it(contentStream, "1") }
            printMap[64]?.let { it(contentStream, "4") }
            printMap[65]?.let { it(contentStream, "9") }
            printMap[66]?.let { it(contentStream, "x") }
            printMap[67]?.let { it(contentStream, "x") }
            printMap[68]?.let { it(contentStream, "x") }
            printMap[69]?.let { it(contentStream, "x") }
            printMap[70]?.let { it(contentStream, "x") }
            printMap[71]?.let { it(contentStream, "x") }
            printMap[72]?.let { it(contentStream, "x") }
            printMap[73]?.let { it(contentStream, "x") }
            printMap[74]?.let { it(contentStream, "x") }
            printMap[75]?.let { it(contentStream, "x") }
            printMap[76]?.let { it(contentStream, "x") }
            printMap[77]?.let { it(contentStream, "x") }
            printMap[78]?.let { it(contentStream, "x") }
            printMap[79]?.let { it(contentStream, "x") }
            printMap[80]?.let { it(contentStream, "x") }
            printMap[81]?.let { it(contentStream, "x") }
            printMap[82]?.let { it(contentStream, "x") }
            printMap[83]?.let { it(contentStream, "x") }
            printMap[84]?.let { it(contentStream, "x") }
            printMap[85]?.let { it(contentStream, "x") }
            printMap[86]?.let { it(contentStream, "x") }
            printMap[88]?.let { it(contentStream, "keine weiteren Hinweise") }

            contentStream.endText()
            contentStream.close()
            doc.save(File(appContext.filesDir, destinationFileName))
            doc.close()

            val downloadedFile = copyFileToDownloads(copiedFile, fileName)
            if (downloadedFile != null) {
                // File copied to Downloads directory successfully
            } else {
                // Error occurred while copying the file
            }
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

    }

}