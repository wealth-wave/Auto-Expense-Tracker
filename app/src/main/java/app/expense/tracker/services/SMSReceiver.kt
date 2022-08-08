package app.expense.tracker.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val smsSyncRequest = OneTimeWorkRequestBuilder<SMSSyncWorker>()
            .build()

        context?.let {
            WorkManager.getInstance(context).enqueue(smsSyncRequest)
        }
    }
}
