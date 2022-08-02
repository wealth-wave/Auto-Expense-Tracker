package app.expense.domain.transaction

import app.expense.contract.TransactionType

data class Transaction(
    val id: Long? = null,
    val amount: Double,
    val fromName: String?,
    val toName: String?,
    val time: Long,
    val type: TransactionType,
    val referenceId: String,
    val referenceMessage: String,
    val referenceMessageSender: String
)