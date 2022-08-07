package app.expense.domain.expense

import app.expense.api.CategoryAPI
import app.expense.api.ExpenseAPI
import app.expense.api.PaidToAPI
import app.expense.api.SuggestionsAPI
import app.expense.model.CategoryDTO
import app.expense.model.ExpenseDTO
import app.expense.model.PaidToDTO

class AddExpenseUseCase(
    private val expenseAPI: ExpenseAPI,
    private val suggestionsAPI: SuggestionsAPI,
    private val categoryAPI: CategoryAPI,
    private val paidToAPI: PaidToAPI
) {

    suspend fun addExpense(expense: Expense, fromSuggestionId: Long? = null) {

        if (expense.category.isNotBlank()) {
            categoryAPI.storeCategory(CategoryDTO(name = expense.category))
        }
        if (expense.paidTo.isNullOrBlank().not()) {
            paidToAPI.storePaidTo(PaidToDTO(name = expense.paidTo ?: ""))
        }
        if (fromSuggestionId != null) {
            suggestionsAPI.deleteSuggestion(fromSuggestionId)
        }

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