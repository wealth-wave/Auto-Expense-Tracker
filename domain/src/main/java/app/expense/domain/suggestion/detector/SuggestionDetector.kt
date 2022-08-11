package app.expense.domain.suggestion.detector

import app.expense.domain.suggestion.models.SMSMessage
import app.expense.domain.suggestion.models.Suggestion

/**
 * Detects Suggestion by analysing the SMS Message
 */
abstract class SuggestionDetector {

    /**
     * Detect Suggestion by analyzing the SMS Message.
     */
    abstract fun detectSuggestions(smsMessage: SMSMessage): Suggestion?
}
