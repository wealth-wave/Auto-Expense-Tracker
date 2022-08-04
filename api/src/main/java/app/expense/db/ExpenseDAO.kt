package app.expense.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.expense.model.ExpenseDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDAO {

    @Insert
    suspend fun insert(expense: ExpenseDTO)

    @Query("SELECT * FROM `expense` WHERE time < :from AND time > :upTo")
    fun fetchExpenses(from: Long, upTo: Long): Flow<List<ExpenseDTO>>
}