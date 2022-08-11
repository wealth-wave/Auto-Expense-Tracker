package app.expense.domain.expense.usecases

import app.expense.api.ExpenseAPI
import app.expense.domain.expense.mappers.ExpenseDataMapper
import app.expense.domain.expense.models.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchExpenseUseCase(
    private val expenseAPI: ExpenseAPI,
    private val dataMapper: ExpenseDataMapper
) {

    fun getExpenses(from: Long, to: Long? = null): Flow<List<Expense>> {
        return expenseAPI.getExpenses(from = from, to = to).map { expenses ->
            expenses.map { expenseDTO ->
                dataMapper.mapToExpense(expenseDTO)
            }
        }
    }

    fun getExpense(id: Long): Flow<Expense?> {
        return expenseAPI.getExpense(id).map { expenseDTO ->
            expenseDTO?.let {
                dataMapper.mapToExpense(it)
            }
        }
    }
}
