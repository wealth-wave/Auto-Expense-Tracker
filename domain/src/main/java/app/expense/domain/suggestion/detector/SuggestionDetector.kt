package app.expense.domain.suggestion.detector

import app.expense.contract.SMSMessage
import app.expense.domain.suggestion.Suggestion

abstract class SuggestionDetector() {
    abstract fun detectSuggestions(smsMessage: SMSMessage): Suggestion?
}