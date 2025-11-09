package com.example.zinotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zinotes.data.DataSource
import com.example.zinotes.model.ListUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        loadHanzi()
    }

    private fun loadHanzi() {
        viewModelScope.launch {
            delay(1000)

            try{
                val data = DataSource.hanziList
                _uiState.value = ListUiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error("Failed to load data")
            }
        }
    }
}