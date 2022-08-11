package app.expense.presentation.viewModels

import android.icu.text.NumberFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import app.expense.domain.expense.models.Expense
import app.expense.domain.expense.usecases.FetchExpenseUseCase
import app.expense.presentation.viewStates.ExpenseListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale.getDefault
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val fetchExpenseUseCase: FetchExpenseUseCase
) : ViewModel() {

    fun getExpenseListState(): Flow<ExpenseListState> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        return fetchExpenseUseCase.getExpenses(from = calendar.timeInMillis).map { expenses ->
            ExpenseListState(dateExpenseMap = getExpensesByDate(expenses))
        }
    }

    private fun getExpensesByDate(expenses: List<Expense>): Map<String, List<ExpenseListState.Item>> =
        expenses
            .groupBy { expense ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = expense.time
                calendar.get(Calendar.DAY_OF_YEAR)
            }.toSortedMap { day1, day2 ->
                performDescendingCompare(day1, day2)
            }
            .mapValues { mapEntry ->
                mapEntry.value.map { expense ->
                    ExpenseListState.Item(
                        id = expense.id ?: 0,
                        amount = NumberFormat.getCurrencyInstance().format(expense.amount),
                        paidTo = expense.paidTo
                    )
                }
            }.mapKeys { mapEntry ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_YEAR, mapEntry.key)
                SimpleDateFormat("dd MMMM yyyy", getDefault()).format(calendar.timeInMillis)
            }

    private fun performDescendingCompare(day1: Int, day2: Int) = when {
        day1 < day2 -> 1
        day2 < day1 -> -1
        else -> 0
    }
}
