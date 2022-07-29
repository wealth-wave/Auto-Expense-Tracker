package app.expense.domain.smsTemplate

import app.expense.model.SMSMessage

class SMSTemplateMatcher {

    fun isMatch(smsTemplate: SMSTemplate, smsMessage: SMSMessage): Boolean {
        val templateString = smsTemplate.template
        val smsString = smsMessage.body

        val templateWords = templateString.split(" ")
        val smsWords = smsString.split(" ")

        var cursorPos = -1
        templateWords.forEach { templateWord ->
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

    fun placeHolderValueMap(smsTemplate: SMSTemplate, smsMessage: SMSMessage): Map<String, String> {
        val placeHolderValueMap = mutableMapOf<String, String>()

        val templateString = smsTemplate.template
        val smsString = smsMessage.body

        val templateWordList =
            "\\{(.*?)}".toRegex().findAll(templateString).toList().map { it.value }
        templateWordList.forEach { templateWord ->
            val templateAccompanies = templateString.split(templateWord)
            val preAccompany =
                (templateAccompanies.firstOrNull() ?: "").split("}").lastOrNull() ?: ""
            val postAccompany =
                (templateAccompanies.getOrNull(1) ?: "").split("{").firstOrNull() ?: ""

            val value = (smsString.split(preAccompany).getOrNull(1) ?: "").split(postAccompany)
                .firstOrNull()
                ?: ""

            placeHolderValueMap[templateWord] = value
        }

        return placeHolderValueMap
    }

    private fun isPlaceHolderWord(word: String): Boolean {
        return word.startsWith("{") || word.endsWith("}")
    }
}