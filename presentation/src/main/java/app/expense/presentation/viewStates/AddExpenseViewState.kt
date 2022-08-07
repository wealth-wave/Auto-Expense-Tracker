package app.expense.presentation.viewStates

data class AddExpenseViewState(
    val amount: String = "",
    val paidTo: String = "",
    val category: String = "",
    val time: Long = System.currentTimeMillis()
)