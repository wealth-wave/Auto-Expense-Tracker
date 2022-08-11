package app.expense.domain.suggestion.models

data class SMSMessage(
    val address: String,
    val body: String,
    val time: Long
)
