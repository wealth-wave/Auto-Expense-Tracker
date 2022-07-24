package app.expense.domain.services

import app.expense.api.ExpenseSyncAPI
import app.expense.api.SMSReadAPI
import app.expense.domain.Message
import app.expense.domain.models.expense.Expense
import app.expense.model.ExpenseDTO

class ExpenseSyncService(
    private val expenseSyncAPI: ExpenseSyncAPI,
    private val smsReadAPI: SMSReadAPI,
    private val expenseDetector: ExpenseDetector
) {

    suspend fun sync() {
        val lastSyncedTime = expenseSyncAPI.getLastSyncedTime()
        val expenses: List<Expense> =
            smsReadAPI.getAllSms(lastSyncedTime).mapNotNull { smsMessage ->
                Message(from = smsMessage.address, content = smsMessage.body).let { message ->
                    expenseDetector.detectExpense(message)
                }
            }

        expenseSyncAPI.storeExpenses(expenses.map { expense ->
            ExpenseDTO(
                amount = expense.amount.value,
                accountName = expense.accountName,
                accountId = expense.accountId,
                merchantId = expense.merchantId,
                merchantName = expense.merchantName,
                referenceId = expense.referenceId,
                time = expense.time,
                type = expense.type
            )
        })

        expenseSyncAPI.setLastSyncedTime(System.currentTimeMillis())

    }
}