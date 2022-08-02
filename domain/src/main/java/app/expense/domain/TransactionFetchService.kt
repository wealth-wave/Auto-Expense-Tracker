package app.expense.domain

import app.expense.api.TransactionsReadAPI
import app.expense.domain.transaction.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionFetchService(private val transactionsReadAPI: TransactionsReadAPI) {

    fun getTransactions(upTo: Long): Flow<List<Transaction>> {
        return transactionsReadAPI.getTransactions(upTo).map { transactions ->
            transactions.map { transactionDto ->
                Transaction(
                    id = transactionDto.id,
                    amount = transactionDto.amount,
                    fromName = transactionDto.fromName,
                    toName = transactionDto.toName,
                    time = transactionDto.time,
                    type = transactionDto.type,
                    referenceId = transactionDto.referenceId,
                    referenceMessage = transactionDto.referenceMessage,
                    referenceMessageSender = transactionDto.referenceMessageSender
                )
            }
        }
    }
}