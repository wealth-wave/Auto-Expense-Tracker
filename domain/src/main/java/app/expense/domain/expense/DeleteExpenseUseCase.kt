package app.expense.domain.expense

import app.expense.api.ExpenseAPI
import app.expense.domain.mappers.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeleteExpenseUseCase(
    private val expenseAPI: ExpenseAPI,
) {

    suspend fun deleteExpense(id: Long) {
        return expenseAPI.deleteExpense(id)
    }
}