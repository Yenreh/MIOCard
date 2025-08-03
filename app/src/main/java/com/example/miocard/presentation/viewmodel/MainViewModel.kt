package com.example.miocard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miocard.domain.model.Card
import com.example.miocard.domain.usecase.DeleteCardUseCase
import com.example.miocard.domain.usecase.GetAllCardsUseCase
import com.example.miocard.domain.usecase.RefreshCardBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val cards: List<Card> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val refreshingCardId: String? = null,
    val showDeleteDialog: Boolean = false,
    val cardToDelete: Card? = null,
    val isDeletingCard: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val refreshCardBalanceUseCase: RefreshCardBalanceUseCase,
    private val deleteCardUseCase: DeleteCardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadCards()
    }

    private fun loadCards() {
        viewModelScope.launch {
            getAllCardsUseCase()
                .catch { throwable ->
                    _uiState.value = _uiState.value.copy(
                        error = throwable.message,
                        isLoading = false
                    )
                }
                .collect { cards ->
                    _uiState.value = _uiState.value.copy(
                        cards = cards,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    fun refreshCardBalance(cardId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(refreshingCardId = cardId)
            
            refreshCardBalanceUseCase(cardId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(refreshingCardId = null)
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        refreshingCardId = null,
                        error = throwable.message
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun showDeleteDialog(card: Card) {
        _uiState.value = _uiState.value.copy(
            showDeleteDialog = true,
            cardToDelete = card
        )
    }

    fun hideDeleteDialog() {
        _uiState.value = _uiState.value.copy(
            showDeleteDialog = false,
            cardToDelete = null
        )
    }

    fun deleteCard() {
        val cardToDelete = _uiState.value.cardToDelete ?: return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isDeletingCard = true)
            
            try {
                deleteCardUseCase(cardToDelete)
                _uiState.value = _uiState.value.copy(
                    showDeleteDialog = false,
                    cardToDelete = null,
                    isDeletingCard = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete card",
                    isDeletingCard = false
                )
            }
        }
    }
}