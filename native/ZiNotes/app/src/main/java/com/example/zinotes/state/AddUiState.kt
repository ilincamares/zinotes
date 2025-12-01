package com.example.zinotes.state

data class AddUiState (
    val pinyin: String = "",
    val tones: String = "",
    val radicalNumber: String = "",
    val strokeCount: String = "",
    val hskLevel: String = "",
    val definitions: String = "",

    val isSaving: Boolean = false,
    val errorMessage: String? = null
)