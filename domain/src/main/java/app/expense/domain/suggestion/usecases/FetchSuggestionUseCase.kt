package app.expense.domain.suggestion.usecases

import app.expense.api.SuggestionsAPI
import app.expense.domain.suggestion.mappers.SuggestionDataMapper
import app.expense.domain.suggestion.models.Suggestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchSuggestionUseCase(
    private val suggestionsAPI: SuggestionsAPI,
    private val dataMapper: SuggestionDataMapper
) {

    fun getSuggestions(from: Long, to: Long? = null): Flow<List<Suggestion>> {
        return suggestionsAPI.getSuggestions(from, to).map { suggestions ->
            suggestions.map { suggestionDTO ->
                dataMapper.mapToSuggestion(suggestionDTO)
            }
        }
    }

    fun getSuggestion(id: Long): Flow<Suggestion?> {
        return suggestionsAPI.getSuggestion(id).map { suggestionDto ->
            suggestionDto?.let {
                dataMapper.mapToSuggestion(it)
            }
        }
    }
}
