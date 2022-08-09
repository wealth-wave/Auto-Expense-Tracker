package app.expense.presentation.viewStates

data class ExpenseListState(
    val dateExpenseMap: Map<String, List<ExpenseListItemState>> = emptyMap()
)

data class ExpenseListItemState(
    val amount: String,
    val paidTo: String?,
)