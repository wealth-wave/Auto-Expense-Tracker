package app.expense.domain.transaction

import app.expense.contract.TransactionType

data class Transaction(
    val id: Long? = null,
    val amount: Double,
    val fromId: String,
    val fromName: String,
    val toId: String,
    val toName: String,
    val time: Long,
    val type: TransactionType,
    val referenceId: String
)