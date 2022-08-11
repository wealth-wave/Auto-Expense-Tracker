package app.expense.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import app.expense.db.model.ExpenseDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: ExpenseDTO): Long

    @Update
    suspend fun update(expense: ExpenseDTO): Int

    @Query("SELECT * FROM `expense` WHERE time > :from AND time < :to ORDER BY time DESC")
    fun fetchExpenses(from: Long, to: Long): Flow<List<ExpenseDTO>>

    @Query("SELECT * FROM `expense` WHERE time > :from  ORDER BY time DESC")
    fun fetchExpenses(from: Long): Flow<List<ExpenseDTO>>

    @Query("SELECT * FROM `expense` WHERE id = :id")
    fun fetchExpense(id: Long): Flow<ExpenseDTO?>

    @Transaction
    suspend fun insertOrUpdate(expenseDTO: ExpenseDTO): Long {
        val id = insert(expenseDTO)
        return if (id == -1L) {
            update(expenseDTO)
            expenseDTO.id ?: 0L
        } else {
            id
        }
    }

    @Query("DELETE FROM `expense` WHERE id = :id")
    suspend fun delete(id: Long)
}
