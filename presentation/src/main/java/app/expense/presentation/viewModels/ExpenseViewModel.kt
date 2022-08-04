package app.expense.presentation.viewModels

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import app.expense.domain.SuggestionFetchService
import app.expense.presentation.viewStates.SuggestionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(private val suggestionFetchService: SuggestionFetchService) :
    ViewModel() {

    fun getSuggestions(): Flow<SuggestionViewState> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val upTo = calendar.timeInMillis
        return suggestionFetchService.getSuggestions(upTo).map { suggestions ->
            val expenses =
                suggestions.sumOf { it.amount }
            SuggestionViewState(
                expenses = expenses,
                suggestions = suggestions
            )
        }
    }
}