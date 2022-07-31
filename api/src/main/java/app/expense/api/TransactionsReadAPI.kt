package app.expense.api

import app.expense.db.TransactionDao
import app.expense.model.TransactionDTO

class TransactionsReadAPI(
    private val transactionDao: TransactionDao
) {
    suspend fun getTransactions(upTo: Long): List<TransactionDTO> {
        return transactionDao.fetchTransactions(upTo)
    }
}