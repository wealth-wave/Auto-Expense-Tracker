package app.expense.presentation.viewStates

import app.expense.domain.transaction.Transaction

data class TransactionViewState(
    val expenses: Double = 0.0,
    val incomes: Double = 0.0,
    val transactions: List<Transaction> = emptyList()
)