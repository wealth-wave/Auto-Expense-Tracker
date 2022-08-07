package app.expense.api

import app.expense.db.ExpenseDAO
import app.expense.model.ExpenseDTO
import kotlinx.coroutines.flow.Flow

class ExpenseAPI(private val expenseDao: ExpenseDAO) {

    suspend fun storeExpense(expenseDTO: ExpenseDTO) {
        expenseDao.insert(expenseDTO)
    }

    fun getExpenses(from: Long?, upTo: Long): Flow<List<ExpenseDTO>> {
        return if (from != null) expenseDao.fetchExpenses(from = from, upTo = upTo)
        else expenseDao.fetchExpenses(upTo = upTo)
    }

    fun getExpense(id: Long): Flow<ExpenseDTO> {
        return expenseDao.fetchExpense(id)
    }
}