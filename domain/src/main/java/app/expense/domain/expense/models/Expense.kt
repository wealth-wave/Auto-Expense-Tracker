package app.expense.domain.expense.models

data class Expense(
    val id: Long? = null,
    val amount: Double,
    val paidTo: String?,
    val categories: List<String>,
    val time: Long,
)
