package app.expense.domain.expense

import app.expense.domain.Message
import app.expense.domain.smsTemplate.SMSTemplateProvider

class ExpenseDetector(private val smsTemplateProvider: SMSTemplateProvider) {

    private val smsTemplates by lazy {
        smsTemplateProvider.getTemplates()
    }

    /*
    Sample SMS
    INR 2,007.00 spent on ABCDE Bank Card XX1234 on 24-Jul-22 at GoDaddy. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434
    INR 602.00 sent from your Account XXXXXXXX1234 Mode: UPI | To: cashfree@amdbank Date: July 21, 2022 Not done by you? Call 080-121212122 -ABC Bank
    Rs 500.00 debited from your A/c using UPI on 17-07-2022 12:17:24 and VPA upid.aa@oababi credited (UPI Ref No 121212121212)-ABC Bank
    */
    fun detectExpense(message: Message): Expense? {
        TODO()
    }
}