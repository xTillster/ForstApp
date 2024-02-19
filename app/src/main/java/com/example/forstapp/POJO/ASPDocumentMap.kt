package com.example.forstapp.POJO

import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font
import com.tom_roush.pdfbox.util.Matrix

class ASPDocumentMap {
    companion object {
        var printMap = HashMap<Int, (contentStream : PDPageContentStream, String) -> Unit>()
        private var coordinates = HashMap<Int, Matrix>()
        init {
            setupCoordinates()
            for(i in 1..88){
                //initialisiert die Map zum document schreiben mit allen Funktionen und den Koordinaten
                printMap[i] = inputBuilder(coordinates[i])
            }
        }

        //Input: Position wo Text geschrieben werden soll als Matrix
        //Output: Funktion die einen contentStream und den content nimmt und den content ins document schreibt
        private fun inputBuilder(position : Matrix?): (contentStream : PDPageContentStream, content : String) -> Unit {
            return {
                //input function
                contentStream : PDPageContentStream, content : String ->
                contentStream.setFont(PDType1Font.HELVETICA, 12F)
                contentStream.setTextMatrix(position)
                contentStream.showText(content)
            }
        }

        //f values: Horizontal offset, positive moves text left, negative moves text right
        //e values: vertical offset, positive moves text upwards, negative moves text down
        private fun setupCoordinates(){
            coordinates[73] = Matrix(0F, -1F, 1F, 0F, 266F, 515.5F)
            coordinates[74] = Matrix(0F, -1F, 1F, 0F, 244F, 515.5F)
            coordinates[75] = Matrix(0F, -1F, 1F, 0F, 222F, 515.5F)
            coordinates[76] = Matrix(0F, -1F, 1F, 0F, 200F, 515.5F)
            coordinates[77] = Matrix(0F, -1F, 1F, 0F, 178F, 515.5F)
            coordinates[78] = Matrix(0F, -1F, 1F, 0F, 266F, 355.5F)
            coordinates[79] = Matrix(0F, -1F, 1F, 0F, 244F, 355.5F)
            coordinates[80] = Matrix(0F, -1F, 1F, 0F, 222F, 355.5F)
            coordinates[81] = Matrix(0F, -1F, 1F, 0F, 200F, 355.5F)
            coordinates[82] = Matrix(0F, -1F, 1F, 0F, 178F, 355.5F)
            coordinates[83] = Matrix(0F, -1F, 1F, 0F, 266F, 195.5F)
            coordinates[84] = Matrix(0F, -1F, 1F, 0F, 244F, 515.5F)
            coordinates[85] = Matrix(0F, -1F, 1F, 0F, 222F, 515.5F)
            coordinates[86] = Matrix(0F, -1F, 1F, 0F, 200F, 195.5F)
        }
    }
}

