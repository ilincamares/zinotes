package com.example.zinotes.state

import com.example.zinotes.room.Hanzi

sealed interface ListUiState {
    data object Loading : ListUiState
    data class Error(val message: String) : ListUiState
    data class Success(val hanziList: List<Hanzi>) : ListUiState
}
