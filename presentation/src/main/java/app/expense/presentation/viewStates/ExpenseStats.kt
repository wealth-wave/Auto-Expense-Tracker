package app.expense.presentation.viewStates

data class ExpenseStats(
    val monthlySpent: Map<String, String> = emptyMap(),
)
