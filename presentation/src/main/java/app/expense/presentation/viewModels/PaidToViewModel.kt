package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.domain.paidTo.FetchPaidToUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PaidToViewModel @Inject constructor(
    private val fetchPaidToUseCase: FetchPaidToUseCase,
) : ViewModel() {

    private val _paidTosState = MutableStateFlow<List<String>>(emptyList())
    val paidToState: StateFlow<List<String>>
        get() = _paidTosState

    suspend fun getPaidTos(name: String) {
        fetchPaidToUseCase.fetchPaidTo(name).collect { paidTos ->
            _paidTosState.value = paidTos
        }
    }
}
