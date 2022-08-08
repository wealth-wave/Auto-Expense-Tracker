package app.expense.presentation.viewStates

import android.icu.util.Calendar
import java.text.SimpleDateFormat

data class ExpenseDate(
    val year: Int,
    val month: Int,
    val day: Int
) {

    constructor(timeInMillis: Long) : this(
        Calendar.getInstance().also { it.timeInMillis = timeInMillis }.get(Calendar.YEAR),
        Calendar.getInstance().also { it.timeInMillis = timeInMillis }.get(Calendar.MONTH),
        Calendar.getInstance().also { it.timeInMillis = timeInMillis }.get(Calendar.DAY_OF_MONTH)
    )

    fun getFormattedString(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        return SimpleDateFormat("dd MMMM yyyy").format(calendar.timeInMillis)
    }
}
