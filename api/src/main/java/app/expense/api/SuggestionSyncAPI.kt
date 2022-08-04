package app.expense.api

import android.content.SharedPreferences
import app.expense.db.SuggestionDAO
import app.expense.model.SuggestionDTO

class SuggestionSyncAPI(
    private val sharedPreferences: SharedPreferences,
    private val suggestionDAO: SuggestionDAO
) {

    fun getLastSyncedTime(): Long? {
        val lastSyncTime = sharedPreferences.getLong(LAST_SYNC_TIME_KEY, -1L)
        if (lastSyncTime == -1L) {
            return null
        }

        return lastSyncTime
    }

    suspend fun storeSuggestions(suggestions: List<SuggestionDTO>) {
        suggestionDAO.insertAll(suggestions)
    }

    fun setLastSyncedTime(time: Long) {
        sharedPreferences.edit().putLong(LAST_SYNC_TIME_KEY, time).apply()
    }

    companion object {
        private const val LAST_SYNC_TIME_KEY = "SUGGESTIONS_LAST_SYNC_TIME"
    }
}