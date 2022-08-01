package app.expense.domain.transaction.detector

import app.expense.contract.SMSMessage
import app.expense.contract.TransactionType
import app.expense.domain.transaction.Transaction

class TransactionDetectorByParserImpl() : TransactionDetector() {



    override fun detectTransactions(smsMessage: SMSMessage): Transaction? {
        return null
    }


}