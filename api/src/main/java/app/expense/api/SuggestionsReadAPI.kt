package app.expense.api

import app.expense.db.SuggestionDAO
import app.expense.model.SuggestionDTO
import kotlinx.coroutines.flow.Flow

class SuggestionsReadAPI(
    private val suggestionDAO: SuggestionDAO
) {
    fun getSuggestions(upTo: Long): Flow<List<SuggestionDTO>> {
        return suggestionDAO.fetchSuggestions(upTo)
    }
}