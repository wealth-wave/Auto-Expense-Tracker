package app.expense.tracker.usecases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import app.expense.domain.suggestion.usecases.SyncSuggestionUseCase
import app.expense.tracker.utils.NotificationUtils
import kotlinx.coroutines.flow.first

class SuggestionSyncAndroidUseCase(
    private val context: Context,
    private val syncSuggestionUseCase: SyncSuggestionUseCase,
    private val notificationUtils: NotificationUtils
) {
    suspend fun sync() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            syncSuggestionUseCase.sync().first().forEach { suggestion ->
                notificationUtils.showNewSuggestionNotification(
                    suggestionId = suggestion.id,
                    amount = suggestion.amount
                )
            }
        }
    }
}
