package app.expense.domain

import app.expense.api.SuggestionsReadAPI
import app.expense.domain.suggestion.Suggestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SuggestionFetchService(private val suggestionsReadAPI: SuggestionsReadAPI) {

    fun getSuggestions(upTo: Long): Flow<List<Suggestion>> {
        return suggestionsReadAPI.getSuggestions(upTo).map { suggestions ->
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
}