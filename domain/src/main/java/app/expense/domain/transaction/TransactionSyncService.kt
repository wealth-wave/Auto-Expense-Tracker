package app.expense.domain.transaction

import app.expense.api.SMSReadAPI
import app.expense.api.TransactionSyncAPI
import app.expense.domain.transaction.detector.TransactionDetector
import app.expense.model.TransactionDTO
import java.util.concurrent.TimeUnit

class TransactionSyncService(
    private val transactionSyncAPI: TransactionSyncAPI,
    private val smsReadAPI: SMSReadAPI,
    private val transactionDetector: TransactionDetector
) {

    suspend fun sync() {
        val startTime = System.currentTimeMillis()
        val lastSyncedTime =
            when {
                transactionSyncAPI.getLastSyncedTime() != null -> transactionSyncAPI.getLastSyncedTime()
                else -> (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30))
            }
        val transactions: List<Transaction> =
            smsReadAPI.getAllSms(lastSyncedTime).mapNotNull { smsMessage ->
                transactionDetector.detectTransactions(smsMessage)

            }

        transactionSyncAPI.storeTransactions(transactions.map { transaction ->
            TransactionDTO(
                amount = transaction.amount,
                fromName = transaction.fromName,
                toName = transaction.toName,
                time = transaction.time,
                type = transaction.type,
                referenceId = transaction.referenceId,
                referenceMessage = transaction.referenceMessage,
                referenceMessageSender = transaction.referenceMessageSender
            )
        })

        transactions.map { it.time }.maxOrNull()?.let { lastMessageTime ->
            transactionSyncAPI.setLastSyncedTime(lastMessageTime)
        }
    }
}