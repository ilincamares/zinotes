package com.example.zinotes.state

import com.example.zinotes.room.Hanzi

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val hanzi: Hanzi) : DetailUiState
    data class Error(val message: String) : DetailUiState
}