package app.expense.tracker.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SMSSyncWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {

        return Result.success()
    }
}