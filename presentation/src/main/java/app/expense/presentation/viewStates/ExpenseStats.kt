package app.expense.presentation.viewStates

data class ExpenseStats(
    val monthlySpent: Map<String, Double> = emptyMap(),
)