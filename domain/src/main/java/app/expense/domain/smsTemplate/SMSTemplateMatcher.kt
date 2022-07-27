package app.expense.domain.smsTemplate

import app.expense.model.SMSMessage

class SMSTemplateMatcher {

    fun isMatch(smsTemplate: SMSTemplate, smsMessage: SMSMessage): Boolean {
        val templateString = smsTemplate.template
        val smsString = smsMessage.body

        val templateWords = templateString.split(" ")
        val smsWords = smsString.split(" ")

        var cursorPos = -1
        for (templateWord in templateWords) {
            if (isPlaceHolderWord(templateWord).not()) {
                var isPassed = false
                smsWords.forEachIndexed { index, smsWord ->
                    if (index > cursorPos && templateWord == smsWord && isPassed.not()) {
                        isPassed = true
                        cursorPos = index
                    }
                }

                if (isPassed.not()) {
                    return false
                }
            }
        }

        return true
    }

    private fun isPlaceHolderWord(word: String): Boolean {
        return word.startsWith("{") || word.endsWith("}")
    }
}