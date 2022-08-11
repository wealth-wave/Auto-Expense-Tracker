package app.expense.api

import android.content.SharedPreferences

/**
 * API Class to expose Suggestion Sync related operations.
 */
class SuggestionSyncAPI(private val sharedPreferences: SharedPreferences) {

    fun getLastSyncedTime(): Long? {
        val lastSyncTime = sharedPreferences.getLong(LAST_SYNC_TIME_KEY, -1L)
        if (lastSyncTime == -1L) {
            return null
        }

        return lastSyncTime
    }

    fun setLastSyncedTime(time: Long) {
        sharedPreferences.edit().putLong(LAST_SYNC_TIME_KEY, time).apply()
    }

    companion object {
        private const val LAST_SYNC_TIME_KEY = "SUGGESTIONS_LAST_SYNC_TIME"
    }
}
