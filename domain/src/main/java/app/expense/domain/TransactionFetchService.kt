package app.expense.domain

import app.expense.api.TransactionsReadAPI
import app.expense.domain.transaction.Transaction

class TransactionFetchService(private val transactionsReadAPI: TransactionsReadAPI) {

    suspend fun getTransactions(upTo: Long): List<Transaction> {
        return transactionsReadAPI.getTransactions(upTo).map {
            Transaction(
                id = it.id,
                amount = it.amount,
                fromId = it.fromId,
                fromName = it.fromName,
                toId = it.toId,
                toName = it.toName,
                time = it.time,
                type = it.type,
                referenceId = it.referenceId
            )
        }
    }
}