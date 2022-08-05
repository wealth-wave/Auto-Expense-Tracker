package app.expense.api

import app.expense.db.SuggestionDAO
import app.expense.model.SuggestionDTO
import kotlinx.coroutines.flow.Flow

class SuggestionsAPI(
    private val suggestionDAO: SuggestionDAO
) {

    fun getSuggestions(from: Long?, upTo: Long): Flow<List<SuggestionDTO>> {
        return if (from != null) suggestionDAO.fetchSuggestions(from = from, upTo = upTo)
        else suggestionDAO.fetchSuggestions(upTo)
    }

    suspend fun deleteSuggestion(id: Long) {
        suggestionDAO.deleteSuggestionById(id)
    }
}