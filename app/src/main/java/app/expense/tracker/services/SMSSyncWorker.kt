package app.expense.tracker.services

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.expense.domain.transaction.TransactionSyncService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class SMSSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val transactionSyncService: TransactionSyncService
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        transactionSyncService.sync()
        return Result.success()
    }
}