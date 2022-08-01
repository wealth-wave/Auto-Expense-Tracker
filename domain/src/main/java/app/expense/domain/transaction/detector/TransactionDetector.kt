package app.expense.domain.transaction.detector

import app.expense.contract.SMSMessage
import app.expense.domain.transaction.Transaction

abstract class TransactionDetector() {
    abstract fun detectTransactions(smsMessage: SMSMessage): Transaction?
}