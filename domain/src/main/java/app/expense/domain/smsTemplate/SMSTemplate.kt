package app.expense.domain.smsTemplate

data class SMSTemplate(
    val template: String,
    val amountKey: String,
    val toNameKey: String,
)