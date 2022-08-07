package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.domain.expense.Expense
import app.expense.domain.expense.FetchExpenseUseCase
import app.expense.domain.suggestion.Suggestion
import app.expense.domain.suggestion.FetchSuggestionUseCase
import app.expense.presentation.viewStates.DateRange
import app.expense.presentation.viewStates.DashBoardViewState
import app.expense.presentation.viewStates.ExpenseDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val suggestionFetchUseCase: FetchSuggestionUseCase,
    private val fetchExpenseUseCase: FetchExpenseUseCase
) : ViewModel() {

    fun getDashBoardViewState(dateRange: DateRange): Flow<DashBoardViewState> {
        return combine(
            getSuggestions(dateRange),
            getExpenses(dateRange)
        ) { suggestions, expenses ->

            DashBoardViewState(
                totalExpense = expenses.sumOf { it.amount },
                expenses = expenses.groupBy { ExpenseDate(it.time) },
                suggestions = suggestions.groupBy { ExpenseDate(it.time) }
            )
        }
    }

    private fun getSuggestions(dateRange: DateRange): Flow<List<Suggestion>> {
        return suggestionFetchUseCase.getSuggestions(
            dateRange.getFrom(),
            dateRange.getTo()
        )
    }

    private fun getExpenses(dateRange: DateRange): Flow<List<Expense>> {
        return fetchExpenseUseCase.getExpenses(dateRange.getFrom(), dateRange.getTo())
    }
}