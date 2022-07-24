package app.expense.domain.models.expense

import app.expense.domain.models.Money

class Expense(
    val id: String,
    val amount: Money,
    val type: String,
    val merchantId: String,
    val merchantName: String,
    val time: Long,
    val referenceId: String,
    val accountId: String,
    val accountName: String
) {
}