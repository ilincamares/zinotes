package com.example.zinotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zinotes.data.DataSource
import com.example.zinotes.model.AddUiState
import com.example.zinotes.model.Hanzi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class EditViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    private var currentHanziId: String? = null


    fun updatePinyin(input: String) {
        _uiState.update { it.copy(pinyin = input) }
    }
    fun updateTones(input: String) {
        _uiState.update { it.copy(tones = input) }
    }
    fun updateRadicalNumber(input: String) {
        _uiState.update { it.copy(radicalNumber = input) }
    }
    fun updateStrokeCount(input: String) {
        _uiState.update { it.copy(strokeCount = input) }
    }
    fun updateHSKLevel(input: String) {
        _uiState.update { it.copy(hskLevel = input) }
    }
    fun updateDefinitions(input: String) {
        _uiState.update { it.copy(definitions = input) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun loadHanzi(id: String) {
        viewModelScope.launch {
            val hanzi = DataSource.findHanziById(id)

            if (hanzi != null) {
               currentHanziId = hanzi.id
                _uiState.update {
                    it.copy(
                        pinyin = hanzi.pinyin,
                        tones = hanzi.tones.joinToString(","),
                        definitions = hanzi.definitions?.joinToString(",") ?: "",
                        radicalNumber = hanzi.radicalNumber?.toString() ?: "",
                        strokeCount = hanzi.strokeCount?.toString() ?: "",
                        hskLevel = hanzi.hskLevel?.toString() ?: ""
                    )
                }
            }
        }
    }

    fun saveItem(onSuccess: () -> Unit) {
        _uiState.update { it.copy(isSaving = true, errorMessage = null) }

        viewModelScope.launch {
            delay(1000)
            try {
                if(_uiState.value.pinyin.isEmpty())
                    throw Exception("Please enter the pinyin")
                if(_uiState.value.tones.isEmpty())
                    throw Exception("Please enter the tones")
                val newItem = Hanzi(
                    id = currentHanziId ?: UUID.randomUUID().toString(),
                    pinyin = _uiState.value.pinyin,
                    tones = _uiState.value.tones.split(',').map{
                        it.trim().toInt()
                    },
                    definitions = _uiState.value.definitions.split(','),
                    radicalNumber = _uiState.value.radicalNumber.toIntOrNull(),
                    strokeCount = _uiState.value.strokeCount.toIntOrNull(),
                    hskLevel = _uiState.value.hskLevel.toIntOrNull()
                )
                DataSource.updateHanzi(newItem)
                onSuccess()

            } catch (e: NumberFormatException) {
                _uiState.update { it.copy(isSaving = false, errorMessage = "Please enter valid numbers for strokeCount/radicalNumber/hskLevel") }
                Log.e("AddViewModel", "NumberFormatException: ${e.message}")
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, errorMessage = e.message) }
                Log.e("AddViewModel", "Exception: ${e.message}")
            }
        }
    }
}