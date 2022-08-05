package app.expense.domain.suggestion

import app.expense.api.SuggestionsAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SuggestionFetchService(private val suggestionsAPI: SuggestionsAPI) {

    fun getSuggestions(from: Long?, upTo: Long): Flow<List<Suggestion>> {
        return suggestionsAPI.getSuggestions(from, upTo).map { suggestions ->
            suggestions.map { suggestionDTO ->
                Suggestion(
                    id = suggestionDTO.id,
                    amount = suggestionDTO.amount,
                    toName = suggestionDTO.toName,
                    time = suggestionDTO.time,
                    referenceMessage = suggestionDTO.referenceMessage,
                    referenceMessageSender = suggestionDTO.referenceMessageSender
                )
            }
        }
    }

    suspend fun deleteSuggestion(id: Long) {
        suggestionsAPI.deleteSuggestion(id)
    }
}