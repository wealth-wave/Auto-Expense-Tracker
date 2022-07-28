package app.expense.domain.smsTemplate

//INR 2,007.00 spent on ABCDE Bank Card XX1234 on 24-Jul-22 at GoDaddy. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434

data class SMSTemplate(
    val template: String,
    val transactionType: TransactionType,
    val amountKey: String,
    val accountNameKey: String,
    val accountReferenceKey: String,
    val merchantNameKey: String,
    val merchantReferenceKey: String,
    val smsReferenceKey: String?
)