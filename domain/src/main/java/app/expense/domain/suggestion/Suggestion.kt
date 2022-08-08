package app.expense.domain.suggestion

data class Suggestion(
    val id: Long? = null,
    val amount: Double,
    val paidTo: String?,
    val time: Long,
    val referenceMessage: String,
    val referenceMessageSender: String
)
