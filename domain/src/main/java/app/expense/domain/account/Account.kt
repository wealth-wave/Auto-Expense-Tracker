package app.expense.domain.account

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