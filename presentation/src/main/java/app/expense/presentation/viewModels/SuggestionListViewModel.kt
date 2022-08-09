package app.expense.presentation.viewModels

import android.icu.text.NumberFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import app.expense.domain.expense.DeleteSuggestionUseCase
import app.expense.domain.suggestion.FetchSuggestionUseCase
import app.expense.domain.suggestion.Suggestion
import app.expense.presentation.viewStates.SuggestionListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SuggestionListViewModel @Inject constructor(
    private val fetchSuggestionUseCase: FetchSuggestionUseCase,
    private val deleteSuggestionUseCase: DeleteSuggestionUseCase
) : ViewModel() {

    fun getSuggestionListState(): Flow<SuggestionListState> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        return fetchSuggestionUseCase.getSuggestions(from = calendar.timeInMillis)
            .map { suggestions ->
                SuggestionListState(dateSuggestionsMap = getSuggestionsByDate(suggestions))
            }
    }

    suspend fun deleteSuggestion(suggestionId: Long) {
        deleteSuggestionUseCase.deleteSuggestion(suggestionId)
    }

    private fun getSuggestionsByDate(expenses: List<Suggestion>): Map<String, List<SuggestionListState.Item>> =
        expenses
            .groupBy { expense ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = expense.time
                calendar.get(Calendar.DAY_OF_YEAR)
            }.toSortedMap { day1, day2 ->
                performDescendingCompare(day1, day2)
            }
            .mapValues { mapEntry ->
                mapEntry.value.map { suggestion ->
                    SuggestionListState.Item(
                        id = suggestion.id ?: 0,
                        amount = NumberFormat.getCurrencyInstance().format(suggestion.amount),
                        message = suggestion.referenceMessage
                    )
                }
            }.mapKeys { mapEntry ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_YEAR, mapEntry.key)
                SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar.timeInMillis)
            }

    private fun performDescendingCompare(day1: Int, day2: Int) = when {
        day1 < day2 -> 1
        day2 < day1 -> -1
        else -> 0
    }
}