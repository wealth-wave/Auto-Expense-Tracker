package app.expense.domain.suggestion.detector

/**
 * This class is a complete port of https://github.com/minimal-scouser/trny/blob/main/engine.js
 * Needs to be Improved
 */
class SuggestionParserHelper {
    companion object {
        const val DEBIT_PATTERN = "debited|debit|deducted"
        const val MISC_PATTERN = "payment|spent|paying"
    }

    /**
     * @param processedMessage Make sure it is in lowercase
     */
    fun isExpense(processedMessage: String): Boolean {
        return (DEBIT_PATTERN.toRegex()
            .containsMatchIn(processedMessage) || MISC_PATTERN.toRegex()
            .containsMatchIn(processedMessage))
    }

    fun processMessage(msg: String): String {
        var message = msg

        // convert to lower case
        message = message.lowercase()
        // remove '-'
        message = message.replace("-", " ")
        // remove ':'
        message = message.replace(":", " ")
        // remove '/'
        message = message.replace("/", " ")
        // remove 'ending'
        message = message.replace(" ending ", " ")
        // replace 'xx'
        message = message.replace("x|[*]".toRegex(), "")
        // // remove 'is' 'with'
        message = message.replace(" is ", " ")
        // replace 'is'
        message = message.replace(" is ", " ")
        // replace 'with'
        message = message.replace(" with ".toRegex(), " ")
        // remove 'no.'
        message = message.replace("no.", "")
        // replace all ac, acct, account with ac
        message = message.replace(" acct ", " ac ")
        message = message.replace(" account ", " ac ")
        // replace all 'rs' with 'rs. '
        message = message.replace(" rs ", " rs. ")
        // replace all 'rs ' with 'rs. '
        // message = message.replace("/rs/g", "rs.")
        // replace all inr with rs.
        message = message.replace(" inr ".toRegex(), " rs. ")
        //
        message = message.replace(" inr ", " rs. ")
        message = message.replace("inr ", "rs. ")

        message = message.replace(" rs.", " rs. ")
        // replace all 'rs. ' with 'rs.'
        //message = message.replace("/rs./g", "rs")
        // replace all 'rs.' with 'rs. '
        //message = message.replace("/rs.(?=w)/g", "rs.")

        return message
    }

    fun getPaidName(processedMessage: String): String? {
        val messageArray = processedMessage.split(" ").filter { s -> s != "" }

        var atIndex = -1

        run breaking@{
            messageArray.forEachIndexed { index, messageWord ->
                if (messageWord == "at") {
                    atIndex = index
                    return@breaking
                }
            }
        }

        if (atIndex == -1) {
            return null
        }
        var paidName = ""
        for (i in atIndex + 1 until messageArray.size) {
            val word = messageArray[i]
            if (word == "on") {
                break
            } else if (word.contains(".")) {
                if (paidName.isNotEmpty()) {
                    paidName += " "
                }
                paidName += word.replace(".", "")
                break
            } else {
                if (paidName.isNotEmpty()) {
                    paidName += " "
                }
                paidName += word
            }
        }

        if (paidName.isNotBlank() && paidName.isNotEmpty()) {
            return paidName
        }
        return null
    }

    fun getAmountSpent(processedMessage: String): Double? {
        val messageArray = processedMessage.split(" ").filter { s -> s != "" }
        val index = messageArray.indexOf("rs.")

        if (index != -1) {
            var money = messageArray[index + 1]
            money = money.replace(",", "")

            return if (money.toDoubleOrNull() == null) {
                money = messageArray[index + 2]
                money = money.replace(",", "")
                money.toDoubleOrNull()
            } else {
                money.toDoubleOrNull()
            }
        }

        return null
    }
}