package app.expense.domain.suggestion.detector

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class RegexHelperTest {

    private lateinit var regexHelper: RegexHelper

    @Before
    fun setUp() {
        regexHelper = RegexHelper()
    }

    @Test
    fun `Should return true for debits`() {
        assertThat(regexHelper.isExpense("debited Rs 500 from your account")).isTrue()
        assertThat(regexHelper.isExpense("UPDATE: Your A/c XX1234 credited with INR 15,160.00 on 12-34-1234 by A/c linked to mobile no XX12XX(IMPS Ref No. XX123XXXXX123) Available bal: INR 2,088,505.04")).isTrue()
        assertThat(regexHelper.isExpense("INR 60.0 sent from your debit card")).isTrue()
        assertThat(regexHelper.isExpense("free amount of Rs 500")).isFalse()
    }

    @Test
    fun `Should return amount spent`() {
        val message = "UPDATE: Your A/c XX1234 credited with INR 15,160.00 on 12-34-1234 by A/c linked to mobile no XX12XX(IMPS Ref No. XX123XXXXX123) Available bal: INR 2,088,505.04"

        assertThat(regexHelper.getAmountSpent(message)).isEqualTo(15160.0)
    }

    @Test
    fun `Should return amount spent 2`() {
        val message =
            "INR 60.00 sent from your Account xxxxxx8811 Mode: UPI | To:thayagam@sbi"

        assertThat(regexHelper.getAmountSpent(message)).isEqualTo(60.0)
    }

    @Test
    fun `Should return paid name`() {
        val message =
            "thank you for using your kotak debit card 1234 for rs. 56.00 at xyz on 12345.avl bal rs. 7,281.19.you?visit kotak.comfraud"

        assertThat(regexHelper.getPaidToName(message)).isEqualTo("xyz")
    }

    @Test
    fun `Should return paid name as null`() {
        val message =
            "update your ac 1234 credited with rs. 15,160.00 on 12341234 by ac linked to mobile no 12(imps ref  123123) available bal rs. 2,088,505.04"

        assertThat(regexHelper.getPaidToName(message)).isNull()
    }

    @Test
    fun `Should return card name from message`() {
        val message = "Dear Customer, Min payment Rs.175.00/total payment Rs.3499.00 for Hyderabad Example Card **********91000 is due by 12-34-1234. Please ignore if already paid."

        assertThat(regexHelper.getCardName(message)).isEqualTo("**********91000")
    }
}
