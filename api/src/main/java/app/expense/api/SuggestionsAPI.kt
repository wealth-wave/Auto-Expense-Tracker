package app.expense.api

import app.expense.db.daos.SuggestionDAO
import app.expense.model.SuggestionDTO
import kotlinx.coroutines.flow.Flow

class SuggestionsAPI(
    private val suggestionDAO: SuggestionDAO
) {

    fun getSuggestions(from: Long, to: Long? = null): Flow<List<SuggestionDTO>> {
        return if (to != null) suggestionDAO.fetchSuggestions(from = from, to = to)
        else suggestionDAO.fetchSuggestions(from)
    }

    fun getSuggestion(id: Long): Flow<SuggestionDTO?> {
        return suggestionDAO.fetchSuggestion(id)
    }

    suspend fun deleteSuggestion(id: Long) {
        suggestionDAO.deleteSuggestionById(id)
    }
}
