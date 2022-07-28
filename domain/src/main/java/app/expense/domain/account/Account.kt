package app.expense.domain.account

class Account(
    val id: Long,
    val name: String,
    val referenceId: String
) {
    fun balanceAmount(): Double {
        TODO()
    }
}