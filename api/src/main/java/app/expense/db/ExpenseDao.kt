package app.expense.db

import androidx.room.Dao
import app.expense.model.ExpenseDTO

@Dao
interface ExpenseDao {
    suspend fun insertAll(expenses: List<ExpenseDTO>)
}