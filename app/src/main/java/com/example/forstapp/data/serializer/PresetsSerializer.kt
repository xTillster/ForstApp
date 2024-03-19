package com.example.forstapp.data.serializer

import androidx.datastore.core.Serializer
import com.example.forstapp.data.pojo.Presets
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object PresetsSerializer : Serializer<Presets>{
    override val defaultValue: Presets
        get() = Presets()

    //TODO(maybe aes here)
    override suspend fun readFrom(input: InputStream): Presets {
        return try {
            Json.decodeFromString(
                deserializer = Presets.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Presets, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Presets.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}