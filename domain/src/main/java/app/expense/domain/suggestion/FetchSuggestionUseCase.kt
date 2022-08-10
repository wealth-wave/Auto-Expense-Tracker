package app.expense.domain.suggestion

import app.expense.api.SuggestionsAPI
import app.expense.domain.mappers.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchSuggestionUseCase(
    private val suggestionsAPI: SuggestionsAPI,
    private val dataMapper: DataMapper
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
