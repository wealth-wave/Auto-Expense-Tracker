package app.expense.domain.smsTemplate

class SMSTemplateProvider {
    fun getTemplates(): List<SMSTemplate> {
        //TODO get it from DB and have sync logics...
        return listOf(
            //INR 2,007.00 spent on ABCDE Bank Card XX1234 on 24-Jul-22 at GoDaddy. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434
            SMSTemplate(
                template = "INR {amount} spent on {account_name} Card {account_id} on 24-Jul-22 at {merchant_name}. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434",
                transactionType = TransactionType.DEBIT,
                amountKey = "amount",
                accountNameKey = "account_name",
                accountReferenceKey = "account_id",
                merchantNameKey = "merchant_name",
                merchantReferenceKey = "merchant_name",
                smsReferenceKey = null
            ),
            //INR 602.00 sent from your Account XXXXXXXX1234 Mode: UPI | To: cashfree@amdbank Date: July 21, 2022 Not done by you? Call 080-121212122 -ABC Bank
            SMSTemplate(
                template = "INR {amount} sent from your Account {account_id} Mode: UPI | To: {merchant_name} Date: July 21, 2022 Not done by you? Call 080-121212122 -{account_name}",
                transactionType = TransactionType.DEBIT,
                amountKey = "amount",
                accountNameKey = "account_name",
                accountReferenceKey = "account_id",
                merchantNameKey = "merchant_name",
                merchantReferenceKey = "merchant_name",
                smsReferenceKey = null
            ),
        )
    }
}