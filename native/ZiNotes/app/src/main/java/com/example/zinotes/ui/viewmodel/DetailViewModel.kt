package com.example.zinotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zinotes.data.HanziRepository
import com.example.zinotes.state.DetailUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: HanziRepository): ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadHanzi(id: Long) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            delay(200)

            val hanzi = repository.getHanzi(id)

            if (hanzi != null) {
                _uiState.value = DetailUiState.Success(hanzi)
            } else {
                _uiState.value = DetailUiState.Error("Hanzi with ID $id not found")
            }
        }
    }

    fun deleteHanzi(id: Long?, onSuccess: () -> Unit) {
        if (id == null) {
            _uiState.value = DetailUiState.Error("Invalid hanzi ID")
            return
        }

        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            try{
                repository.deleteHanziById(id)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error("Failed to delete hanzi")
                Log.e("DetailViewModel", "Failed to delete hanzi", e)
            }
        }
    }
}