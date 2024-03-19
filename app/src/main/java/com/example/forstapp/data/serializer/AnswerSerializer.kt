package com.example.forstapp.data.serializer

import androidx.datastore.core.Serializer
import com.example.forstapp.data.pojo.Answers
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AnswerSerializer : Serializer<Answers> {
    override val defaultValue: Answers
        get() = Answers()

    override suspend fun readFrom(input: InputStream): Answers {
        return try {
            Json.decodeFromString(
                deserializer = Answers.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Answers, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Answers.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}