package app.expense.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.expense.model.TransactionDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertAll(transactions: List<TransactionDTO>)

    @Query("SELECT * FROM `transaction` WHERE time > :upTo")
    fun fetchTransactions(upTo: Long): Flow<List<TransactionDTO>>
}