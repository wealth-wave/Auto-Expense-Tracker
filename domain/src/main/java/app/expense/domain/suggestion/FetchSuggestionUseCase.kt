package app.expense.domain.suggestion

import app.expense.api.SuggestionsAPI
import app.expense.domain.mappers.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchSuggestionUseCase(
    private val suggestionsAPI: SuggestionsAPI,
    private val dataMapper: DataMapper
) {

    fun getSuggestions(from: Long?, upTo: Long): Flow<List<Suggestion>> {
        return suggestionsAPI.getSuggestions(from, upTo).map { suggestions ->
            suggestions.map { suggestionDTO ->
                dataMapper.mapToSuggestion(suggestionDTO)
            }
        }
    }

    fun getSuggestion(id: Long): Flow<Suggestion> {
        return suggestionsAPI.getSuggestion(id).map { suggestionDto->
            dataMapper.mapToSuggestion(suggestionDto)
        }
    }
}