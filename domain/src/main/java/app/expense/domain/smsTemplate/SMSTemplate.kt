package app.expense.domain.smsTemplate

import app.expense.contract.TransactionType

//INR 2,007.00 spent on ABCDE Bank Card XX1234 on 24-Jul-22 at GoDaddy. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434

data class SMSTemplate(
    val template: String,
    val transactionType: TransactionType,
    val amountKey: String,
    val fromNameKey: String,
    val fromIdKey: String,
    val toNameKey: String,
    val toIdKey: String,
    val referenceKey: String?
)