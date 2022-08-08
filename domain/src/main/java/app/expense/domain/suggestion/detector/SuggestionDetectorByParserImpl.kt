package app.expense.domain.suggestion.detector

import app.expense.contract.SMSMessage
import app.expense.domain.suggestion.Suggestion

class SuggestionDetectorByParserImpl(private val suggestionParserHelper: SuggestionParserHelper) :
    SuggestionDetector() {

    override fun detectSuggestions(smsMessage: SMSMessage): Suggestion? {
        val processedMessage = suggestionParserHelper.processMessage(smsMessage.body)

        val isExpense = suggestionParserHelper.isExpense(processedMessage)

        if (isExpense.not()) {
            return null
        }

        val spent = suggestionParserHelper.getAmountSpent(processedMessage)
        val paidToName = suggestionParserHelper.getPaidName(processedMessage)

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
