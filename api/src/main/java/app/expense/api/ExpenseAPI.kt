package app.expense.api

import app.expense.db.daos.ExpenseDAO
import app.expense.db.model.ExpenseDTO
import kotlinx.coroutines.flow.Flow

/**
 * Expense API to expose expense related API.
 */
class ExpenseAPI(private val expenseDao: ExpenseDAO) {

    /**
     * Store Expense to DB.
     */
    suspend fun storeExpense(expenseDTO: ExpenseDTO) {
        expenseDao.insertOrUpdate(expenseDTO)
    }

    /**
     * Get expenses from DB.
     *
     * @param from: The oldest time period it should filter.
     * @param to: The latest time period it should filter.
     */
    fun getExpenses(from: Long, to: Long? = null): Flow<List<ExpenseDTO>> {
        return if (to != null) expenseDao.fetchExpenses(from = from, to = to)
        else expenseDao.fetchExpenses(from = from)
    }

    /**
     * Get Expense from DB based on id.
     */
    fun getExpense(id: Long): Flow<ExpenseDTO?> {
        return expenseDao.fetchExpense(id)
    }

    /**
     * Delete the Expense based on id.
     */
    suspend fun deleteExpense(id: Long) {
        expenseDao.delete(id)
    }
}
