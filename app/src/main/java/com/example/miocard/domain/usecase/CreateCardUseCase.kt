package com.example.miocard.domain.usecase

import com.example.miocard.domain.model.Card
import com.example.miocard.domain.repository.CardRepository
import javax.inject.Inject

class CreateCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(card: Card) {
        val nextPosition = cardRepository.getNextPosition()
        val cardWithPosition = card.copy(position = nextPosition)
        cardRepository.insertCard(cardWithPosition)
    }
}