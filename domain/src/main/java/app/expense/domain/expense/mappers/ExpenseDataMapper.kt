package app.expense.domain.expense.mappers

import app.expense.db.model.ExpenseDTO
import app.expense.domain.expense.models.Expense

class ExpenseDataMapper {

    fun mapToExpense(expense: ExpenseDTO) = Expense(
        id = expense.id,
        amount = expense.amount,
        paidTo = expense.paidTo,
        categories = expense.categories,
        time = expense.time
    )
}
