package app.expense.presentation.viewModels

import android.Manifest
import android.icu.util.Calendar
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import app.expense.domain.suggestion.usecases.FetchSuggestionUseCase
import app.expense.domain.suggestion.usecases.SyncSuggestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val fetchSuggestionUseCase: FetchSuggestionUseCase,
    private val syncSuggestionUseCase: SyncSuggestionUseCase
) : ViewModel() {

    @RequiresPermission(Manifest.permission.READ_SMS)
    suspend fun syncSuggestions() {
        syncSuggestionUseCase.sync()
    }

    fun getSuggestionsCount(): Flow<Int> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        return fetchSuggestionUseCase.getSuggestions(from = calendar.timeInMillis).map {
            it.count()
        }
    }
}
