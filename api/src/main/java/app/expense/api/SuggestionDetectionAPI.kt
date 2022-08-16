package app.expense.api

import android.content.SharedPreferences

class SuggestionDetectionAPI(private val sharedPreferences: SharedPreferences) {
    fun getLastSyncedTime(): Long? {
        val lastSyncTime = sharedPreferences.getLong(LAST_DETECTED_TIME, -1L)
        if (lastSyncTime == -1L) {
            return null
        }

        return lastSyncTime
    }

    fun setLastSyncedTime(time: Long) {
        sharedPreferences.edit().putLong(LAST_DETECTED_TIME, time).apply()
    }

    companion object {
        private const val LAST_DETECTED_TIME = "SUGGESTIONS_LAST_DETECTED_TIME"
    }
}
