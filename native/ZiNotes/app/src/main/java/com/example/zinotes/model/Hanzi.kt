package com.example.zinotes.model

data class Hanzi (
    val pinyin: String,
    val tones: List<Int>,
    val radicalNumber: Int,
    val strokeCount: Int,
    val hskLevel: Int,
    val definitions: List<String>
)