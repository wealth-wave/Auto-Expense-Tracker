package app.expense.domain.expense

import app.expense.api.ExpenseAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchExpenseUseCase(private val expenseAPI: ExpenseAPI) {

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