package app.expense.domain.smsTemplate

import app.expense.model.SMSMessage
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class SMSTemplateMatcherTest {


    private val smsTemplateMatcher = SMSTemplateMatcher()
    private val smsTemplate = mockk<SMSTemplate>()
    private val smsMessage = mockk<SMSMessage>()

    @Test
    fun `Should return True when template is matched`() {

        every { smsTemplate.template }.returns("INR {amount} spent on {account_name} Card {account_id} on 24-Jul-22 at {merchant_name}. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434")
        every { smsMessage.body }.returns("INR 2,007.00 spent on ABCDE Bank Card XX1234 on 24-Jul-22 at GoDaddy. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434")

        assertThat(smsTemplateMatcher.isMatch(smsTemplate, smsMessage)).isTrue()
    }

    @Test
    fun `Should Map the placeholder with values`() {
        every { smsTemplate.template }.returns("INR {amount} spent on {account_name} Card {account_id} on 24-Jul-22 at {merchant_name}. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434")
        every { smsMessage.body }.returns("INR 2,007.00 spent on ABCDE Bank Card XX1234 on 24-Jul-22 at GoDaddy. Avl Lmt: INR 1,11,992.80. To dispute,call 18002662/SMS BLOCK 1111 to 3434343434")

        assertThat(smsTemplateMatcher.placeHolderValueMap(smsTemplate, smsMessage)).isNotEmpty()
    }
}