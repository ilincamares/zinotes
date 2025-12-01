package com.example.zinotes.room

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters{

    @TypeConverter
    fun fromDefinitions(definitions: List<String>?): String? {
        if (definitions == null) return null
        return Json.encodeToString(definitions)
    }

    @TypeConverter
    fun toDefinitions(definitions: String?): List<String>? {
        if (definitions == null) return null
        return Json.decodeFromString(definitions)
    }

    @TypeConverter
    fun fromTones(tones: List<Int>): String {
        return Json.encodeToString(tones)
    }

    @TypeConverter
    fun toTones(tones: String): List<Int> {
        return Json.decodeFromString(tones)
    }

}