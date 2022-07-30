package app.expense.domain.transaction

import android.app.Service
import android.content.Intent
import android.os.IBinder
import app.expense.api.SMSReadAPI
import app.expense.api.TransactionSyncAPI
import app.expense.model.TransactionDTO

class TransactionSyncService(
    private val transactionSyncAPI: TransactionSyncAPI,
    private val smsReadAPI: SMSReadAPI,
    private val transactionDetector: TransactionDetector
) : Service() {


    suspend fun sync() {
        val lastSyncedTime = transactionSyncAPI.getLastSyncedTime()
        val transactions: List<Transaction> =
            smsReadAPI.getAllSms(lastSyncedTime).mapNotNull { smsMessage ->
                transactionDetector.detectTransactions(smsMessage)

            }

        transactionSyncAPI.storeTransactions(transactions.map { transaction ->
            TransactionDTO(
                amount = transaction.amount,
                fromId = transaction.fromId,
                fromName = transaction.fromName,
                toId = transaction.toId,
                toName = transaction.toName,
                time = transaction.time,
                type = transaction.type,
                referenceId = transaction.referenceId
            )
        })

        transactionSyncAPI.setLastSyncedTime(System.currentTimeMillis())

    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}