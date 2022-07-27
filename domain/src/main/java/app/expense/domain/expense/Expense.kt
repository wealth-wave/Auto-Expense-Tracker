package app.expense.domain.expense

import app.expense.domain.Money

class Expense(
    val id: String,
    val amount: Money,
    val type: String,
    val merchantId: String,
    val merchantName: String,
    val merchantType: String,
    val time: Long,
    val referenceId: String,
    val accountId: String,
    val accountName: String
) {
}