package com.example.miocard.domain.usecase

import com.example.miocard.domain.model.Card
import com.example.miocard.domain.model.CardBalance
import com.example.miocard.domain.repository.CardRepository
import javax.inject.Inject

class RefreshCardBalanceUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(cardId: String): Result<Card> {
        return try {
            val cardBalance = cardRepository.getCardBalance(cardId).getOrThrow()
            val existingCard = cardRepository.getCardById(cardId)
                ?: return Result.failure(Exception("Card not found"))
            
            val updatedCard = existingCard.copy(
                balance = cardBalance.balance,
                lastUpdate = cardBalance.balanceDate
            )
            
            cardRepository.updateCard(updatedCard)
            Result.success(updatedCard)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}