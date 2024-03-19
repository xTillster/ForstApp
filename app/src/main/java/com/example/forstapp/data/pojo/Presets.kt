package com.example.forstapp.data.pojo

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.Serializable

@Serializable
data class Presets(
    val presets: PersistentMap<String, String> = persistentMapOf()
)
