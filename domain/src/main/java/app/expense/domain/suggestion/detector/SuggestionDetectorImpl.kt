package app.expense.domain.suggestion.detector

import app.expense.domain.suggestion.models.SMSMessage
import app.expense.domain.suggestion.models.Suggestion

/**
 * Suggestion Detector with the help of Regexp Parsing.
 */
class SuggestionDetectorImpl(private val regexHelper: RegexHelper) :
    SuggestionDetector() {

    /**
     * Check for smsMessage is of Transactional SMS and parse the Expense suggestion.
     */
    override fun detectSuggestions(smsMessage: SMSMessage): Suggestion? {
        val isExpense = regexHelper.isExpense(smsMessage.body)
        val spent = regexHelper.getAmountSpent(smsMessage.body)
        val paidToName = regexHelper.getPaidToName(smsMessage.body)

        if (isExpense && spent != null) {
            return Suggestion(
                amount = spent,
                paidTo = paidToName,
                time = smsMessage.time,
                referenceMessage = smsMessage.body,
                referenceMessageSender = smsMessage.address
            )
        }

        return null
    }
}
