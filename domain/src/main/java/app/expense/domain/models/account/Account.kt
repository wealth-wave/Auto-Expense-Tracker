package app.expense.domain.models.account

import app.expense.domain.models.Money

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