package com.example.forstapp.data.serializer

import androidx.datastore.core.Serializer
import com.example.forstapp.data.pojo.Surveys
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
object SurveySerializer: Serializer<Surveys> {
    override val defaultValue: Surveys
        get() = Surveys()

    override suspend fun readFrom(input: InputStream): Surveys {
        return try {
            Json.decodeFromString(
                deserializer = Surveys.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Surveys, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Surveys.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}