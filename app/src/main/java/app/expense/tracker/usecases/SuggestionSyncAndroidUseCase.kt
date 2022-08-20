package app.expense.tracker.usecases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import app.expense.domain.suggestion.usecases.SyncSuggestionUseCase
import app.expense.tracker.utils.NotificationUtils

class SuggestionSyncAndroidUseCase(
    private val context: Context,
    private val syncSuggestionUseCase: SyncSuggestionUseCase,
    private val notificationUtils: NotificationUtils
) {
    suspend fun sync() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val suggestions = syncSuggestionUseCase.sync()
            suggestions.forEach { suggestion ->
                notificationUtils.showNewSuggestionNotification(
                    suggestionId = suggestion.id ?: 0L,
                    amount = suggestion.amount
                )
            }
        }

    }
}