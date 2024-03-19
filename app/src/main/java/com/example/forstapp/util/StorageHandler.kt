package com.example.forstapp.util

import android.content.Context

class StorageHandler(storage: String, context: Context) {
    private val sharedPreferences = context.getSharedPreferences(storage, Context.MODE_PRIVATE)

    fun addSurvey(){

    }

    fun addEntry(){

    }
}