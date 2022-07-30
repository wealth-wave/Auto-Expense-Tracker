package app.expense.tracker.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        //Call work manager transactionSyncService.sync()
    }
}