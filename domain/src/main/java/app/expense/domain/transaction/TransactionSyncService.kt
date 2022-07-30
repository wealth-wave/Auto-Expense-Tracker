package app.expense.domain.transaction

import app.expense.api.SMSReadAPI
import app.expense.api.TransactionSyncAPI
import app.expense.model.TransactionDTO

class TransactionSyncService(
    private val transactionSyncAPI: TransactionSyncAPI,
    private val smsReadAPI: SMSReadAPI,
    private val transactionDetector: TransactionDetector
) {

    suspend fun sync() {
        val startTime = System.currentTimeMillis()
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

        transactionSyncAPI.setLastSyncedTime(startTime)

    }
}