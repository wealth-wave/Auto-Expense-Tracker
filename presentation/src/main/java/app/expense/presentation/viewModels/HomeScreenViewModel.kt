package app.expense.presentation.viewModels

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import app.expense.domain.suggestion.usecases.FetchSuggestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val fetchSuggestionUseCase: FetchSuggestionUseCase
) : ViewModel() {

    fun getSuggestionsCount(): Flow<Int> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        return fetchSuggestionUseCase.getSuggestions(from = calendar.timeInMillis).map {
            it.count()
        }
    }
}
