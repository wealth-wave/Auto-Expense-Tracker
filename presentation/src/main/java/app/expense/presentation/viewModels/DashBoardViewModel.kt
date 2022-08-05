package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.domain.expense.Expense
import app.expense.domain.expense.ExpenseService
import app.expense.domain.suggestion.Suggestion
import app.expense.domain.suggestion.SuggestionFetchService
import app.expense.presentation.viewStates.DateRange
import app.expense.presentation.viewStates.DashBoardViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val suggestionFetchService: SuggestionFetchService,
    private val expenseService: ExpenseService
) : ViewModel() {

    fun getDashBoardViewState(dateRange: DateRange): Flow<DashBoardViewState> {
        return combine(
            getSuggestions(dateRange),
            getExpenses(dateRange)
        ) { suggestions, expenses ->

            DashBoardViewState(
                totalExpense = expenses.sumOf { it.amount },
                expenses = expenses,
                suggestions = suggestions
            )
        }
    }

    private fun getSuggestions(dateRange: DateRange): Flow<List<Suggestion>> {
        return suggestionFetchService.getSuggestions(
            dateRange.getFrom(),
            dateRange.getTo()
        )
    }

    private fun getExpenses(dateRange: DateRange): Flow<List<Expense>> {
        return expenseService.getExpenses(dateRange.getFrom(), dateRange.getTo())
    }
}