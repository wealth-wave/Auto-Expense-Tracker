package app.expense.domain

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.expense.domain.transaction.TransactionSyncService

class TransactionSyncWorker(
    appContext: Context,
    workerParameters: WorkerParameters,
    private val transactionSyncService: TransactionSyncService
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        transactionSyncService.sync()
        return Result.success()
    }
}