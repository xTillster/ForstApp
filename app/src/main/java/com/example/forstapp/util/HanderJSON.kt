package com.example.forstapp.util

import android.content.Context
import com.example.forstapp.pojo.Survey
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HandlerJSON(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("fetchedSurveys", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true } // ignore unknown keys during deserialization

    fun handleResponseAndSaveData(dataJson: String) {
        try {
            val data = json.decodeFromString<Survey>(dataJson)
            println(data)
            saveData(data)
        } catch (e: Exception) {
            // Handle deserialization error
            e.printStackTrace()
        }
    }

    private fun saveData(data: Survey) {
        val editor = sharedPreferences.edit()
        editor.putString("data_key", json.encodeToString(data))
        editor.apply()
    }

    fun getData(key: String): Survey? {
        val jsonString = sharedPreferences.getString(key, null)
        return jsonString?.let {
            try {
                json.decodeFromString<Survey>(it)
            } catch (e: Exception) {
                // Handle deserialization error
                e.printStackTrace()
                null
            }
        }
    }
}
