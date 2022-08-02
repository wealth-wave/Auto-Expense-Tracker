package app.expense.domain.transaction.detector

import android.util.Log
import app.expense.contract.SMSMessage
import app.expense.contract.TransactionType
import app.expense.domain.transaction.Transaction

class TransactionDetectorByParserImpl(private val transactionParserHelper: TransactionParserHelper) :
    TransactionDetector() {


    override fun detectTransactions(smsMessage: SMSMessage): Transaction? {
        val processedMessage = transactionParserHelper.processMessage(smsMessage.body)

        val transactionType =
            transactionParserHelper.getTransactionType(processedMessage) ?: return null


        Log.d("XDFCE", "Identified transaction type $transactionType")
        val account = transactionParserHelper.getAccount(processedMessage)?.second
        val spent = transactionParserHelper.getAmountSpent(processedMessage)

        Log.d("XDFCE", "Identified spent ${spent ?: -1}")
        val paidToName = transactionParserHelper.getPaidName(processedMessage)

        val from = if (transactionType == TransactionType.DEBIT) {
            paidToName
        } else {
            account
        }
        val to = if (transactionType == TransactionType.DEBIT) {
            account
        } else {
            paidToName
        }

        if (spent != null && transactionType != null) {
            return Transaction(
                amount = spent,
                type = transactionType,
                fromName = from,
                toName = to,
                time = smsMessage.time,
                referenceId = smsMessage.body.hashCode().toString(),
                referenceMessage = smsMessage.body,
                referenceMessageSender = smsMessage.address
            )
        }
        Log.d("XDFCE", "problem with sms ${smsMessage.body}")

        return null
    }


}