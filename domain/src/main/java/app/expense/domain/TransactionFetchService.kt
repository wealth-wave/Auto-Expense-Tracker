package app.expense.domain

import app.expense.api.TransactionsReadAPI
import app.expense.domain.transaction.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionFetchService(private val transactionsReadAPI: TransactionsReadAPI) {

    fun getTransactions(upTo: Long): Flow<List<Transaction>> {
        return transactionsReadAPI.getTransactions(upTo).map { transactions ->
            transactions.map { transaction ->
                Transaction(
                    id = transaction.id,
                    amount = transaction.amount,
                    fromId = transaction.fromId,
                    fromName = transaction.fromName,
                    toId = transaction.toId,
                    toName = transaction.toName,
                    time = transaction.time,
                    type = transaction.type,
                    referenceId = transaction.referenceId
                )
            }
        }
    }
}