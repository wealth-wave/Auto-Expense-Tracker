package app.expense.api

import app.expense.db.TransactionDao
import app.expense.model.TransactionDTO
import kotlinx.coroutines.flow.Flow

class TransactionsReadAPI(
    private val transactionDao: TransactionDao
) {
    fun getTransactions(upTo: Long): Flow<List<TransactionDTO>> {
        return transactionDao.fetchTransactions(upTo)
    }
}