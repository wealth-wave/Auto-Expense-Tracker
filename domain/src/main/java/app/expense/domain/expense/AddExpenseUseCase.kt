package app.expense.domain.expense

import app.expense.api.ExpenseAPI
import app.expense.model.ExpenseDTO

class AddExpenseUseCase(private val expenseAPI: ExpenseAPI) {

    suspend fun addExpense(expense: Expense) {
        expenseAPI.storeExpense(
            ExpenseDTO(
                amount = expense.amount,
                category = expense.category,
                paidTo = expense.paidTo,
                time = expense.time
            )
        )
    }
}