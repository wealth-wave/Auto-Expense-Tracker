package app.expense.domain.expense.usecases

import app.expense.api.ExpenseAPI

class DeleteExpenseUseCase(
    private val expenseAPI: ExpenseAPI,
) {

    suspend fun deleteExpense(id: Long) {
        return expenseAPI.deleteExpense(id)
    }
}
