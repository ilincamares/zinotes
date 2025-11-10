package com.example.zinotes.model

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val hanzi: Hanzi) : DetailUiState
    data class Error(val message: String) : DetailUiState
}