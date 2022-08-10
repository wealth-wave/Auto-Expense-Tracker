package app.expense.presentation.viewStates

data class AddExpenseViewState(
    val amount: Double = 0.0,
    val paidTo: String = "",
    val categories: List<String> = emptyList(),
    val time: Long = System.currentTimeMillis(),
    val suggestionMessage: String? = null
)
