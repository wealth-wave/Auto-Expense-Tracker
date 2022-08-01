package app.expense.domain.transaction.detector

import app.expense.contract.TransactionType

class TransactionParserHelper {
    companion object {
        val TRANS_KEYWORDS = arrayOf("debited", "credited", "payment", "spent")
        const val CREDIT_PATTERN = "credited|credit|deposited"
        const val DEBIT_PATTERN = "debited|debit|deducted"
        const val MISC_PATTERN = "payment|spent"
        val balanceKeywords = arrayOf(
            "avbl bal",
            "available balance",
            "a/c bal",
            "available bal",
            "avl bal",
        )
    }

    /**
     * @param message Make sure it is in lowercase
     */
    fun getTransactionType(message: String): TransactionType? {
        if (CREDIT_PATTERN.toRegex().containsMatchIn(message)) {
            return TransactionType.CREDIT
        } else if (DEBIT_PATTERN.toRegex()
                .containsMatchIn(message) || MISC_PATTERN.toRegex()
                .containsMatchIn(message)
        ) {
            return TransactionType.DEBIT
        }

        return null
    }

    fun processMessage(msg: String): String {
        var message = msg

        // convert to lower case
        message = message.lowercase()
        // remove '-'
        message = message.replace("-", "")
        // remove ':'
        message = message.replace(":", "")
        // remove '/'
        message = message.replace("/", "")
        // remove 'ending'
        message = message.replace("\bending\b".toRegex(), "")
        // replace 'xx'
        message = message.replace("x|[*]".toRegex(), "")
        // // remove 'is' 'with'
        message = message.replace("\bis\b|\bwith\b".toRegex(), "")
        // replace 'is'
        message = message.replace("\bis\b".toRegex(), "")
        // replace 'with'
        message = message.replace("\bwith\b".toRegex(), "")
        // remove 'no.'
        message = message.replace("no.", "")
        // replace all ac, acct, account with ac
        message = message.replace("\bac\b|\bacct\b|\baccount\b".toRegex(), "ac")
        // replace all 'rs' with 'rs. '
        message = message.replace("\brs\b".toRegex(), "rs.")
        // replace all 'rs ' with 'rs. '
        // message = message.replace("/rs/g", "rs.")
        // replace all inr with rs.
        message = message.replace("\binr\b".toRegex(), "rs.")
        //
        message = message.replace(" inr ", " rs. ")
        // replace all 'rs. ' with 'rs.'
        //message = message.replace("/rs./g", "rs")
        // replace all 'rs.' with 'rs. '
        //message = message.replace("/rs.(?=w)/g", "rs.")

        return message
    }

    fun getAccount(processedMessage: String): Pair<String, String>? {
        var accountIndex = -1
        val messageArray = processedMessage.split(" ").filter { s -> s != "" }
        messageArray.forEachIndexed { index, word ->
            if (word == "ac") {
                if (index + 1 < messageArray.size) {
                    val acctNo = trimLeadingAndTrailingChars(messageArray[index + 1])

                    if (acctNo.toIntOrNull() != null) {
                        accountIndex = index
                        return Pair("account", acctNo)
                    }
                }
            } else if (word.contains("ac")) {
                val extractedAcNo = extractBondedAccountNo(word)

                if (extractedAcNo != null) {
                    return Pair("account", extractedAcNo)
                }
            }
        }

        if (accountIndex == -1) {
            return getCard(processedMessage)
        }

        return null

    }

    fun getBalance(processedMessage: String): Double? {
        var indexOfKeyWord = -1

        run breaking@{
            balanceKeywords.forEach { balanceKeyWord ->
                indexOfKeyWord = processedMessage.indexOf(balanceKeyWord)

                if (indexOfKeyWord != -1) {
                    indexOfKeyWord += balanceKeyWord.length
                    return@breaking
                }
            }
        }


        var index = indexOfKeyWord
        var indexOfRs = -1
        var nextThreeChars = processedMessage.substring(index, index + 3)

        index += 3

        while (index < processedMessage.length) {
            nextThreeChars = nextThreeChars.slice(IntRange(1, 2))
            nextThreeChars += processedMessage[index]

            if (nextThreeChars == "rs.") {
                indexOfRs = index + 2
                break
            }

            ++index
        }

        if (indexOfRs == -1) {
            return null
        }

        return extractBalance(
            indexOfRs,
            processedMessage,
            processedMessage.length
        )?.toDoubleOrNull()

    }

    private fun trimLeadingAndTrailingChars(word: String): String {
        var wordToTrim = word
        if (wordToTrim.last().isDigit().not()) {
            wordToTrim = wordToTrim.slice(IntRange(0, -1))
        }
        if (wordToTrim.first().isDigit().not()) {
            wordToTrim = wordToTrim.slice(IntRange(0, 1))
        }

        return wordToTrim
    }

    private fun extractBondedAccountNo(accountNo: String): String? {
        val stripedAcNo = accountNo.replace("ac", "")

        return stripedAcNo.toIntOrNull()?.toString()
    }

    private fun getCard(processedMessage: String): Pair<String, String>? {
        val messageArray = processedMessage.split(" ").filter { s -> s != "" }
        val cardIndex = messageArray.indexOf("card")

        if (cardIndex != -1) {
            val no = messageArray[cardIndex + 1]

            if (no.toIntOrNull() != null) {
                return Pair("card", no)
            }
        }

        return null
    }

    private fun extractBalance(ind: Int, message: String, length: Int): String? {
        var index = ind
        var balance = ""
        var sawNumber = false
        var invalidCharCount = 0
        var char: Char

        while (index < length) {
            char = message[index]

            if ('0' <= char || char >= '9') {
                sawNumber = true
                balance += char
            } else {
                if (sawNumber == true) {
                    if (char == '.') {
                        if (invalidCharCount == 1) {
                            break
                        } else {
                            balance += char
                            invalidCharCount += 1
                        }
                    } else if (char != ',') {
                        break
                    }
                }
            }

            ++index
        }

        return balance
    }
}