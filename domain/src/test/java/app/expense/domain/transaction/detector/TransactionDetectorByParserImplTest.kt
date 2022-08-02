package app.expense.domain.transaction.detector

import app.expense.contract.SMSMessage
import app.expense.contract.TransactionType
import app.expense.domain.transaction.Transaction
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class TransactionDetectorByParserImplTest {

    private lateinit var transactionDetectorByParserImpl: TransactionDetectorByParserImpl

    @Before
    fun setUp() {
        //Not giving mocked object of TransactionParserHelper and instead testing the entire integration.
        transactionDetectorByParserImpl = TransactionDetectorByParserImpl(TransactionParserHelper())
    }

    @Test
    fun `Should return transaction for given messages`() {
        val smsMessage = SMSMessage(
            address = "HDFC",
            body = "Thanks for paying Rs.21,660.00 from A/c XXXX1111 to CBDTTAX via HDFC Bank NetBanking. Call 18002586161 if txn not done by you.",
            time = 1L
        )

        assertThat(transactionDetectorByParserImpl.detectTransactions(smsMessage)).isEqualTo(
            Transaction(
                amount = 21660.00,
                type = TransactionType.DEBIT,
                fromName = null,
                toName = "1111",
                time = 1L,
                referenceMessage = "Thanks for paying Rs.21,660.00 from A/c XXXX1111 to CBDTTAX via HDFC Bank NetBanking. Call 18002586161 if txn not done by you.",
                referenceId = "-523368276",
                referenceMessageSender = "HDFC"
            )
        )
    }

}