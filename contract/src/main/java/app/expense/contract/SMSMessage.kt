package app.expense.contract

data class SMSMessage(
    val address: String,
    val body: String,
    val time: Long
)