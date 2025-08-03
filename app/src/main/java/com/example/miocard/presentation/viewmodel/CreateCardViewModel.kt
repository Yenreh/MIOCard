package com.example.miocard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miocard.domain.model.Card
import com.example.miocard.domain.usecase.CreateCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateCardUiState(
    val id: String = "",
    val prefix: String = "",
    val suffix: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isCardCreated: Boolean = false,
    val idError: String? = null,
    val nameError: String? = null
)

@HiltViewModel
class CreateCardViewModel @Inject constructor(
    private val createCardUseCase: CreateCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateCardUiState())
    val uiState: StateFlow<CreateCardUiState> = _uiState.asStateFlow()

    fun updateId(id: String) {
        _uiState.value = _uiState.value.copy(
            id = id,
            idError = null
        )
    }

    fun updatePrefix(prefix: String) {
        _uiState.value = _uiState.value.copy(prefix = prefix)
    }

    fun updateSuffix(suffix: String) {
        _uiState.value = _uiState.value.copy(suffix = suffix)
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            nameError = null
        )
    }

    fun createCard() {
        val currentState = _uiState.value
        
        // Validate input
        val idError = if (currentState.id.isBlank()) "ID is required" else null
        val nameError = if (currentState.name.isBlank()) "Name is required" else null
        
        if (idError != null || nameError != null) {
            _uiState.value = currentState.copy(
                idError = idError,
                nameError = nameError
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, error = null)
            
            try {
                val card = Card(
                    id = currentState.id.trim(),
                    prefix = currentState.prefix.trim(),
                    suffix = currentState.suffix.trim(),
                    name = currentState.name.trim()
                )
                
                createCardUseCase(card)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isCardCreated = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun resetForm() {
        _uiState.value = CreateCardUiState()
    }
}