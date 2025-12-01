package com.example.zinotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zinotes.data.HanziRepository
import com.example.zinotes.state.ListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ListViewModel(private val repository: HanziRepository): ViewModel() {
    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        loadHanzi()
    }

    private fun loadHanzi() {
        viewModelScope.launch {
            repository.getHanziStream()
                .catch { e ->
                    _uiState.value = ListUiState.Error(e.message ?: "Unknown error")
                    Log.e("ListViewModel", "Error loading hanzi", e)
                }
                .collect { hanziList ->
                    _uiState.value = ListUiState.Success(hanziList)
                }
        }
    }
}