package com.example.zinotes.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hanzi")
data class Hanzi (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val pinyin: String = "",

    val tones: List<Int> = listOf(),

    val radicalNumber: Int? = null,

    val strokeCount: Int? = null,

    val hskLevel: Int? = null,

    val definitions: List<String>? = null
)