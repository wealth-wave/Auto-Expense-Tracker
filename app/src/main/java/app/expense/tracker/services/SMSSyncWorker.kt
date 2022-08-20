package app.expense.tracker.services

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.expense.tracker.usecases.SuggestionSyncAndroidUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SMSSyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val suggestionSyncAndroidUseCase: SuggestionSyncAndroidUseCase,
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        suggestionSyncAndroidUseCase.sync()
        return Result.success()
    }
}
