package app.expense.domain.suggestion.usecases

import app.expense.api.SuggestionDetectionAPI
import app.expense.api.SuggestionsAPI
import app.expense.domain.suggestion.mappers.SuggestionDataMapper
import app.expense.domain.suggestion.models.Suggestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SuggestionsDetectedUseCase(
    private val suggestionDetectionAPI: SuggestionDetectionAPI,
    private val suggestionsAPI: SuggestionsAPI,
    private val suggestionDataMapper: SuggestionDataMapper
) {

    fun getDetectedSuggestions(): Flow<List<Suggestion>> {
        val currentTime = System.currentTimeMillis()
        val lastDetectedTime =
            suggestionDetectionAPI.getLastSyncedTime() ?: currentTime

        return suggestionsAPI.getSuggestions(from = lastDetectedTime).map { suggestions ->
            suggestions.map { suggestion ->
                suggestionDataMapper.mapToSuggestion(suggestion)
            }
        }.also {
            suggestionDetectionAPI.setLastSyncedTime(currentTime)
        }
    }
}
