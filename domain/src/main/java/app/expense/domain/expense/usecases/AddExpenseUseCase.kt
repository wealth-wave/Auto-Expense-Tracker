package app.expense.domain.expense.usecases

import app.expense.api.CategoryAPI
import app.expense.api.ExpenseAPI
import app.expense.api.PaidToAPI
import app.expense.api.SuggestionsAPI
import app.expense.db.model.CategoryDTO
import app.expense.db.model.ExpenseDTO
import app.expense.db.model.PaidToDTO
import app.expense.domain.expense.models.Expense

class AddExpenseUseCase(
    private val expenseAPI: ExpenseAPI,
    private val suggestionsAPI: SuggestionsAPI,
    private val categoryAPI: CategoryAPI,
    private val paidToAPI: PaidToAPI
) {

    suspend fun addExpense(expense: Expense, fromSuggestionId: Long? = null) {

        if (expense.categories.isNotEmpty()) {
            categoryAPI.storeCategories(
                expense.categories.map { category ->
                    CategoryDTO(name = category)
                }
            )
        }
        if (expense.paidTo.isNullOrBlank().not()) {
            paidToAPI.storePaidTo(PaidToDTO(name = expense.paidTo ?: ""))
        }
        if (fromSuggestionId != null) {
            suggestionsAPI.deleteSuggestion(fromSuggestionId)
        }

        expenseAPI.storeExpense(
            ExpenseDTO(
                id = expense.id,
                amount = expense.amount,
                categories = expense.categories,
                paidTo = expense.paidTo,
                time = expense.time
            )
        )
    }
}
