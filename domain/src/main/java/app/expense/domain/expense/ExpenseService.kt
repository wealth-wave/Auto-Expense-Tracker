package app.expense.domain.expense

import app.expense.api.ExpenseAPI
import app.expense.model.ExpenseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseService(private val expenseAPI: ExpenseAPI) {

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

    fun getExpenses(from: Long?, to: Long): Flow<List<Expense>> {
        return expenseAPI.getExpenses(from = from, upTo = to).map { expenses ->
            expenses.map { expenseDTO ->
                Expense(
                    id = expenseDTO.id,
                    amount = expenseDTO.amount,
                    paidTo = expenseDTO.paidTo,
                    category = expenseDTO.category,
                    time = expenseDTO.time
                )
            }
        }
    }
}