package app.expense.domain.suggestion.detector

import app.expense.contract.SMSMessage
import app.expense.domain.suggestion.Suggestion

class SuggestionDetectorImpl(private val suggestionParserHelper: SuggestionParserHelper) :
    SuggestionDetector() {

    override fun detectSuggestions(smsMessage: SMSMessage): Suggestion? {
        val isExpense = suggestionParserHelper.isExpense(smsMessage.body)

        if (isExpense.not()) {
            return null
        }

        val spent = suggestionParserHelper.getAmountSpent(smsMessage.body)
        val paidToName = suggestionParserHelper.getPaidName(smsMessage.body)

        if (spent != null) {
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
