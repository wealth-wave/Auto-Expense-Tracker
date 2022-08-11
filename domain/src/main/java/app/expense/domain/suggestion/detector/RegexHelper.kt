package app.expense.domain.suggestion.detector

import java.util.regex.Pattern

/**
 * Helper class to identify and parse data based on Regexp.
 */
class RegexHelper {

    companion object {
        const val DEBIT_PATTERN = "debited|debit|deducted"
        const val MISC_PATTERN = "payment|spent|paying|sent|UPI"
    }

    /**
     * Check weather the message is of transaction type.
     *
     * @param message from SMS
     */
    fun isExpense(message: String): Boolean {
        val regex =
            "(?=.*[Aa]ccount.*|.*[Aa]/[Cc].*|.*[Aa][Cc][Cc][Tt].*|.*[Cc][Aa][Rr][Dd].*)(?=.*[Cc]redit.*|.*[Dd]ebit.*)(?=.*[Ii][Nn][Rr].*|.*[Rr][Ss].*)"
        return Pattern.compile(regex).matcher(message).find() || DEBIT_PATTERN.toRegex()
            .containsMatchIn(message.lowercase()) || MISC_PATTERN.toRegex()
            .containsMatchIn(message.lowercase())
    }

    /**
     * Get PaidTo/Merchant name from Transaction message.
     *
     * @param message from SMS
     */
    fun getPaidToName(message: String): String? {
        val regex = "(?i)(?:\\sat\\s|in\\*)([A-Za-z0-9]*\\s?-?\\s?[A-Za-z0-9]*\\s?-?\\.?)"

        return Regex(regex).find(message)?.groups?.get(0)?.value?.replace("at ", "")
            ?.replace(" in", "")?.replace(" on", "")?.trim()
    }

    /**
     * Get Amount spent on this transaction message.
     *
     * @param message from SMS
     */
    fun getAmountSpent(message: String): Double? {
        val regex = "(?i)(?:RS|INR|MRP)\\.?\\s?(\\d+(:?,\\d+)?(,\\d+)?(\\.\\d{1,2})?)"

        val matchGroup = Regex(regex).find(message)?.groups?.firstOrNull()
        return matchGroup?.value?.lowercase()
            ?.replace("inr.", "")?.replace("inr", "")
            ?.replace("mrp", "")
            ?.replace("rs.", "")?.replace("rs", "")
            ?.replace(",", "")?.trim()?.toDoubleOrNull()
    }

    /**
     *GEt Card Name from transaction message
     */
    fun getCardName(message: String): String? {
        val regex = "[0-9]*[Xx*]*[0-9]*[Xx*]+[0-9]{3,}"

        return Regex(regex).find(message)?.groups?.get(0)?.value?.replace("inr", "")
            ?.replace("made on", "")?.replace("made a", "")?.trim()
    }
}
