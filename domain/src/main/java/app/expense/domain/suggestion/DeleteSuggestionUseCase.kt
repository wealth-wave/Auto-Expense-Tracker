package app.expense.domain.suggestion

import app.expense.api.SuggestionsAPI

class DeleteSuggestionUseCase(
    private val suggestionsAPI: SuggestionsAPI,
) {

    suspend fun deleteSuggestion(id: Long) {
        return suggestionsAPI.deleteSuggestion(id)
    }
}
