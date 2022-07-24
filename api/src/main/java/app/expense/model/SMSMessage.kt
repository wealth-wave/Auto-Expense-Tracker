package app.expense.model

data class SMSMessage(
    val address: String,
    val body: String,
    val time: Long
)