package app.expense.presentation.viewStates

data class AddExpenseViewState(
    val amount: String = "",
    val paidTo: String = "",
    val categories: List<String> = emptyList(),
    val time: Long = System.currentTimeMillis()
)