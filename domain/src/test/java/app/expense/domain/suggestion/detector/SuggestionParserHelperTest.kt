package app.expense.domain.suggestion.detector


import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class SuggestionParserHelperTest {

    private lateinit var suggestionParserHelper: SuggestionParserHelper

    @Before
    fun setUp() {
        suggestionParserHelper = SuggestionParserHelper()
    }

    @Test
    fun `Should return true for debits`() {
        assertThat(suggestionParserHelper.isExpense("debited Rs 500")).isTrue()
        assertThat(suggestionParserHelper.isExpense("payment of Rs 500")).isTrue()
        assertThat(suggestionParserHelper.isExpense("free amount of Rs 500")).isFalse()
    }

    @Test
    fun `Should replace unnecessary data from message for account format`() {
        val smsMessage =
            "UPDATE: Your A/c XX1234 credited with INR 15,160.00 on 12-34-1234 by A/c linked to mobile no XX12XX(IMPS Ref No. XX123XXXXX123) Available bal: INR 2,088,505.04"
        val expectedMessage =
            "update your ac 1234 credited with rs. 15,160.00 on 12341234 by ac linked to mobile no 12(imps ref  123123) available bal rs. 2,088,505.04"

        assertThat(suggestionParserHelper.processMessage(smsMessage)).isEqualTo(expectedMessage)
    }

    @Test
    fun `Should replace unnecessary data from message for card format`() {
        val smsMessage =
            "thank you for using your kotak debit card 1234 for rs. 56.00 at xyz on 12345.avl bal rs. 7,281.19.you?visit kotak.comfraud"
        val expectedMessage =
            "thank you for using your kotak debit card 1234 for rs. 56.00 at yz on 12345.avl bal rs. 7,281.19.you?visit kotak.comfraud"
        assertThat(suggestionParserHelper.processMessage(smsMessage)).isEqualTo(expectedMessage)
    }

    @Test
    fun `Should return amount spent`() {
        val processedMessage =
            "update your ac 1234 credited with rs. 15,160.00 on 12341234 by ac linked to mobile no 12(imps ref  123123) available bal rs. 2,088,505.04"

        assertThat(suggestionParserHelper.getAmountSpent(processedMessage)).isEqualTo(15160.0)
    }

    @Test
    fun `Should return paid name`() {
        val processedMessage =
            "thank you for using your kotak debit card 1234 for rs. 56.00 at yz on 12345.avl bal rs. 7,281.19.you?visit kotak.comfraud"

        assertThat(suggestionParserHelper.getPaidName(processedMessage)).isEqualTo("yz")
    }

    @Test
    fun `Should return paid name as null`() {
        val processedMessage =
            "update your ac 1234 credited with rs. 15,160.00 on 12341234 by ac linked to mobile no 12(imps ref  123123) available bal rs. 2,088,505.04"

        assertThat(suggestionParserHelper.getPaidName(processedMessage)).isNull()
    }
}