package app.expense.presentation.viewStates

data class ExpenseListState(
    val dateExpenseMap: Map<String, List<Item>> = emptyMap()
) {
    data class Item(
        val id: Long,
        val amount: String,
        val paidTo: String?,
    )
}
