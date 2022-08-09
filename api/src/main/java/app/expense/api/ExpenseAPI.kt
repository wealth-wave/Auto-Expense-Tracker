package app.expense.api

import app.expense.db.daos.ExpenseDAO
import app.expense.model.ExpenseDTO
import kotlinx.coroutines.flow.Flow

class ExpenseAPI(private val expenseDao: ExpenseDAO) {

    suspend fun storeExpense(expenseDTO: ExpenseDTO) {
        expenseDao.insertOrUpdate(expenseDTO)
    }

    fun getExpenses(from: Long, to: Long? = null): Flow<List<ExpenseDTO>> {
        return if (to != null) expenseDao.fetchExpenses(from = from, to = to)
        else expenseDao.fetchExpenses(from = from)
    }

    fun getExpense(id: Long): Flow<ExpenseDTO?> {
        return expenseDao.fetchExpense(id)
    }

    suspend fun deleteExpense(id: Long) {
        expenseDao.delete(id)
    }
}
