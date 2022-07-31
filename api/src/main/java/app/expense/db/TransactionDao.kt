package app.expense.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.expense.model.TransactionDTO

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertAll(transactions: List<TransactionDTO>)

    @Query("SELECT * FROM `transaction` WHERE time > :upTo")
    suspend fun fetchTransactions(upTo: Long): List<TransactionDTO>
}