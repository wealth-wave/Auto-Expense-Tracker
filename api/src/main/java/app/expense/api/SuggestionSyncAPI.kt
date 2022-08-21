package app.expense.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * API Class to expose Suggestion Sync related operations.
 */
class SuggestionSyncAPI(private val dataStore: DataStore<Preferences>) {

    fun getLastSyncedTime(): Flow<Long?> {
        return dataStore.data.map { preferences ->
            preferences[longPreferencesKey(LAST_SYNC_TIME_KEY)]
        }
    }

    suspend fun setLastSyncedTime(time: Long) {
        dataStore.edit { preference ->
            preference[longPreferencesKey(LAST_SYNC_TIME_KEY)] = time
        }
    }

    companion object {
        private const val LAST_SYNC_TIME_KEY = "SUGGESTIONS_LAST_SYNC_TIME"
    }
}
