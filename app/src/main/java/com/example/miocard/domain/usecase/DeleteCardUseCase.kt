package com.example.miocard.domain.usecase

import com.example.miocard.domain.model.Card
import com.example.miocard.domain.repository.CardRepository
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(card: Card) {
        cardRepository.deleteCard(card)
    }
}