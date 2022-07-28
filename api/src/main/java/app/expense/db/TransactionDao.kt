package app.expense.db

import androidx.room.Dao
import app.expense.model.TransactionDTO

@Dao
interface TransactionDao {
    suspend fun insertAll(transactions: List<TransactionDTO>)
}