package app.expense.presentation.viewStates

import app.expense.domain.expense.Expense
import app.expense.domain.suggestion.Suggestion

data class DashBoardViewState(
    val totalExpense: Double = 0.0,
    val expenses: List<Expense> = emptyList(),
    val suggestions: List<Suggestion> = emptyList()
)