package app.expense.domain.transaction.detector


import app.expense.contract.TransactionType
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class TransactionParserHelperTest {

    private lateinit var transactionParserHelper: TransactionParserHelper

    @Before
    fun setUp() {
        transactionParserHelper = TransactionParserHelper()
    }

    @Test
    fun `Should return CREDIT as transaction type`() {
        assertThat(transactionParserHelper.getTransactionType("credited as")).isEqualTo(
            TransactionType.CREDIT
        )
        assertThat(transactionParserHelper.getTransactionType("debited Rs 500")).isEqualTo(
            TransactionType.DEBIT
        )
        assertThat(transactionParserHelper.getTransactionType("payment of Rs 500")).isEqualTo(
            TransactionType.DEBIT
        )
        assertThat(transactionParserHelper.getTransactionType("free amount of Rs 500")).isNull()
    }

    @Test
    fun `Should replace unnecessary data from message for account format`() {
        val smsMessage =
            "UPDATE: Your A/c XX1234 credited with INR 15,160.00 on 12-34-1234 by A/c linked to mobile no XX12XX(IMPS Ref No. XX123XXXXX123) Available bal: INR 2,088,505.04"
        val expectedMessage =
            "update your ac 1234 credited with rs. 15,160.00 on 12341234 by ac linked to mobile no 12(imps ref  123123) available bal rs. 2,088,505.04"

        assertThat(transactionParserHelper.processMessage(smsMessage)).isEqualTo(expectedMessage)
    }

    @Test
    fun `Should replace unnecessary data from message for card format`() {
        val smsMessage =
            "thank you for using your kotak debit card 1234 for rs. 56.00 at xyz on 12345.avl bal rs. 7,281.19.you?visit kotak.comfraud"
        val expectedMessage =
            "thank you for using your kotak debit card 1234 for rs. 56.00 at yz on 12345.avl bal rs. 7,281.19.you?visit kotak.comfraud"
        assertThat(transactionParserHelper.processMessage(smsMessage)).isEqualTo(expectedMessage)
    }

    @Test
    fun `Should get Account No`() {
        val processedMessage =
            "update your ac 1234 credited with rs. 15,160.00 on 12341234 by ac linked to mobile no 12(imps ref  123123) available bal rs. 2,088,505.04"

        assertThat(transactionParserHelper.getAccount(processedMessage)).isEqualTo(
            Pair(
                "account",
                "1234"
            )
        )
    }

    @Test
    fun `Should get Card No`() {
        val processedMessage =
            "thank you for using your kotak debit card 1234 for rs. 56.00 at yz on 12345.avl bal rs. 7,281.19.you?visit kotak.comfraud"

        assertThat(transactionParserHelper.getAccount(processedMessage)).isEqualTo(
            Pair(
                "card",
                "1234"
            )
        )
    }
}