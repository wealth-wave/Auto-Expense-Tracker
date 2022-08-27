package app.expense.api

import app.expense.db.daos.SuggestionDAO
import app.expense.db.model.SuggestionDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * API Class to expose Suggestions related logics.
 */
class SuggestionsAPI(private val suggestionDAO: SuggestionDAO) {

    /**
     * Store Suggestions to DB.
     */
    fun storeSuggestions(suggestions: List<SuggestionDTO>): Flow<Unit> {
        return flow { suggestionDAO.insertAll(suggestions) }
    }

    /**
     * Get suggestions from DB.
     *
     * @param from: Oldest time to filter.
     * @param to: Latest time to filter.o
     */
    fun getSuggestions(from: Long, to: Long? = null): Flow<List<SuggestionDTO>> {
        return if (to != null) suggestionDAO.fetchSuggestions(from = from, to = to)
        else suggestionDAO.fetchSuggestions(from)
    }

    /**
     * Get Suggestion by id.
     */
    fun getSuggestion(id: Long): Flow<SuggestionDTO?> {
        return suggestionDAO.fetchSuggestion(id)
    }

    /**
     * Delete Suggestion by id.
     */
    suspend fun deleteSuggestion(id: Long) {
        suggestionDAO.deleteSuggestionById(id)
    }
}
