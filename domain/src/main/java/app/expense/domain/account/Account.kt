package app.expense.domain.account

import app.expense.domain.Money

class Account(
    val id: String,
    val name: String,
    val type: String,
    val referenceId: String
) {
    fun balanceAmount(): Money {
        TODO()
    }
}