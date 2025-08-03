package com.example.miocard.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miocard.domain.model.Card
import com.example.miocard.domain.repository.CardRepository
import com.example.miocard.domain.usecase.UpdateCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditCardUiState(
    val id: String = "",
    val prefix: String = "",
    val suffix: String = "",
    val name: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isCardUpdated: Boolean = false,
    val nameError: String? = null,
    val idError: String? = null,
    val originalCard: Card? = null
)

@HiltViewModel
class EditCardViewModel @Inject constructor(
    private val cardRepository: CardRepository,
    private val updateCardUseCase: UpdateCardUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cardId: String = checkNotNull(savedStateHandle["cardId"])
    
    private val _uiState = MutableStateFlow(EditCardUiState())
    val uiState: StateFlow<EditCardUiState> = _uiState.asStateFlow()

    init {
        loadCard()
    }

    private fun loadCard() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val card = cardRepository.getCardById(cardId)
                if (card != null) {
                    _uiState.value = _uiState.value.copy(
                        id = card.id,
                        prefix = card.prefix,
                        suffix = card.suffix,
                        name = card.name,
                        originalCard = card,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Card not found",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "An unknown error occurred",
                    isLoading = false
                )
            }
        }
    }

    fun updateId(id: String) {
        _uiState.value = _uiState.value.copy(id = id, idError = null)
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

    fun saveCard() {
        val currentState = _uiState.value
        
        // Validation
        if (currentState.id.isBlank()) {
            _uiState.value = currentState.copy(idError = "Card ID is required")
            return
        }
        if (currentState.name.isBlank()) {
            _uiState.value = currentState.copy(nameError = "Name is required")
            return
        }

        val originalCard = currentState.originalCard ?: return
        
        val updatedCard = originalCard.copy(
            id = currentState.id.trim(),
            prefix = currentState.prefix.trim(),
            suffix = currentState.suffix.trim(),
            name = currentState.name.trim()
        )

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, error = null)
            
            try {
                updateCardUseCase(updatedCard)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isCardUpdated = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to update card"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}