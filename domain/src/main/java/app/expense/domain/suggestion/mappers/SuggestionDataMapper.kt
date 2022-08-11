package app.expense.domain.suggestion.mappers

import app.expense.db.model.SuggestionDTO
import app.expense.domain.suggestion.models.Suggestion

class SuggestionDataMapper {

    fun mapToSuggestion(suggestion: SuggestionDTO) = Suggestion(
        id = suggestion.id,
        amount = suggestion.amount,
        paidTo = suggestion.paidTo,
        time = suggestion.time,
        referenceMessage = suggestion.referenceMessage,
        referenceMessageSender = suggestion.referenceMessageSender
    )
}
