package app.expense.tracker.services

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.expense.domain.suggestion.usecases.SuggestionsDetectedUseCase
import app.expense.tracker.utils.NotificationUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest

@HiltWorker
class SuggestionDetectionWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val suggestionsDetectedUseCase: SuggestionsDetectedUseCase,
    private val notificationUtils: NotificationUtils
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        suggestionsDetectedUseCase.getDetectedSuggestions().collectLatest { suggestions ->
            suggestions.forEach { suggestion ->
                notificationUtils.showNewSuggestionNotification(
                    suggestionId = suggestion.id ?: 0L,
                    amount = suggestion.amount
                )
            }
        }
        return Result.success()
    }
}
