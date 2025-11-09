package com.example.zinotes.model

sealed interface ListUiState {
    data object Loading : ListUiState
    data class Error(val message: String) : ListUiState
    data class Success(val hanziList: List<Hanzi>) : ListUiState
}
