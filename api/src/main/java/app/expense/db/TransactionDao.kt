package app.expense.db

import androidx.room.Dao
import androidx.room.Insert
import app.expense.model.TransactionDTO

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertAll(transactions: List<TransactionDTO>)
}