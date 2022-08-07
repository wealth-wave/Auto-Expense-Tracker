package app.expense.presentation.viewStates

import app.expense.domain.expense.Expense
import app.expense.domain.suggestion.Suggestion

data class DashBoardViewState(
    val totalExpense: Double = 0.0,
    val expenses: Map<ExpenseDate, List<Expense>> = emptyMap(),
    val suggestions: Map<ExpenseDate, List<Suggestion>> = emptyMap()
)