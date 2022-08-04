package app.expense.api

import app.expense.db.ExpenseDAO
import app.expense.model.ExpenseDTO
import kotlinx.coroutines.flow.Flow

class ExpenseAPI(private val expenseDao: ExpenseDAO) {

    suspend fun storeExpense(expenseDTO: ExpenseDTO) {
        expenseDao.insert(expenseDTO)
    }

    fun getExpenses(from: Long, upTo: Long): Flow<List<ExpenseDTO>> {
        return expenseDao.fetchExpenses(from, upTo)
    }
}