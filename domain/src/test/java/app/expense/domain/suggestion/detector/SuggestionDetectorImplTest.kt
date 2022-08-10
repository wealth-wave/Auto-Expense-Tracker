package app.expense.domain.suggestion.detector

import app.expense.contract.SMSMessage
import app.expense.domain.suggestion.Suggestion
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SuggestionDetectorImplTest {

    private lateinit var suggestionDetectorImpl: SuggestionDetectorImpl

    @Before
    fun setUp() {
        // Not giving mocked object of SuggestionParserHelper and instead testing the entire integration.
        suggestionDetectorImpl = SuggestionDetectorImpl(SuggestionParserHelper())
    }

    @Test
    fun `Should return suggestion for given messages`() {
        val smsMessage = SMSMessage(
            address = "HDFC",
            body = "Thanks for paying Rs.21,660.00 from A/c XXXX1111 to CBDTTAX via HDFC Bank NetBanking. Call 18002586161 if txn not done by you.",
            time = 1L
        )

        assertThat(suggestionDetectorImpl.detectSuggestions(smsMessage)).isEqualTo(
            Suggestion(
                amount = 21660.00,
                paidTo = null,
                time = 1L,
                referenceMessage = "Thanks for paying Rs.21,660.00 from A/c XXXX1111 to CBDTTAX via HDFC Bank NetBanking. Call 18002586161 if txn not done by you.",
                referenceMessageSender = "HDFC"
            )
        )
    }

    @Test
    fun `Should return suggestions for given messages list`() {
        val messages = arrayOf(
            "an Amount of Rs 500 has been deducted from your Account ending with 3187",
            "Your a/c XX0123 is debited on 12/34/1234 by INR 3,211.00 towards purchase. Avl Bal: INR 5,603.54.",
            "Dear Customer, Rs.248,759.00 is debited from A/c XXXX1234 for BillPay/Credit Card payment via Example Bank NetBanking. Call XXXXXXXX123XXX if txn not done by you",
            "Dear Customer, You have made a payment of Rs. 46000 using NEFT via IMPS from your Account XXXXXXXX0123 with reference number XXX123XXX on xyz 12, 3456 at 12:34.",
            "Acct XX126 debited with INR 46,000.00 on 12-34-1234 & Acct XX123 credited. IMPS: XXX123XX. Call XX0026XX for dispute or SMS BLOCK 126 to XXX1236XXX",
            "Alert: You've spent INR 555.00 on your Delhi Exapmle Bank card **91XXX at BD JIO MONEY on 12/34/1234 at 11:07AM IST. Please call XXX041XXX if this was not made by you.",
            "Thank you for making a payment of Rs. 3499.00 towards your Example Bank Card. This payment will be reflected as a credit on your Card account within 2 working days. Your payment reference number is XXX123456XXX. For any details you may log on to bankexample.com or our mobile app",
            "Your Debit card annual fee of Rs. 399.00 has been debited from your account.",
            "Dear Customer, Min payment Rs.175.00/total payment Rs.3499.00 for Hyderabad Example Card **********91000 is due by 12-34-1234. Please ignore if already paid.",
            "INR Rs. 399 debited from A/c no. 123456 on Avl Bal-INR Rs. 57575",
            "Your sb a/c **00123 is debited for rs.80 on 12-34-1234 by transfer avl bal rs:6802.04",
//            "Rs49.0 debited@SBI UPI frm A/cX12345 on 18Dec20 RefNo 1212121212. If not done by u, fwd this SMS to 12345/Call 1234 or 12345 to block UPI",
            "Your SB A/c **12345 is Debited for Rs.100 on 01-01-2021 12:30:50 by Transfer. Avl Bal Rs:12345.30-Union Bank of India DOWNLOAD U MB HTTP://ONELINK.TO/BUYHR7",
            "thank you for using your kotak debit card 1234 for rs. 56.00 at xyz on 12345.avl bal rs. 7,281.19.you?visit kotak.comfraud",
        )

        messages.forEach {
            val smsMessage = SMSMessage(address = "HDFC", body = it, time = 1L)
            assertThat(suggestionDetectorImpl.detectSuggestions(smsMessage)).isNotNull()
        }
    }
}
