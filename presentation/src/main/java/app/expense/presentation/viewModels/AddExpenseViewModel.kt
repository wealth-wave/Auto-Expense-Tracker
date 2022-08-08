package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.domain.expense.AddExpenseUseCase
import app.expense.domain.expense.DeleteExpenseUseCase
import app.expense.domain.expense.DeleteSuggestionUseCase
import app.expense.domain.expense.Expense
import app.expense.domain.expense.FetchExpenseUseCase
import app.expense.domain.suggestion.FetchSuggestionUseCase
import app.expense.presentation.viewStates.AddExpenseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val fetchExpenseUseCase: FetchExpenseUseCase,
    private val fetchSuggestionUseCase: FetchSuggestionUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val deleteSuggestionUseCase: DeleteSuggestionUseCase
) : ViewModel() {

    private val _addExpenseViewStateFlow = MutableStateFlow(AddExpenseViewState())
    val addExpenseViewState: StateFlow<AddExpenseViewState>
        get() = _addExpenseViewStateFlow

    suspend fun getAddExpenseViewState(
        expenseId: Long? = null,
        suggestionId: Long? = null
    ) {
        if (expenseId != null) {
            fetchExpenseUseCase.getExpense(expenseId).collect { expense ->

                if (expense != null) {
                    _addExpenseViewStateFlow.value = AddExpenseViewState(
                        amount = expense.amount.toString(),
                        paidTo = expense.paidTo ?: "",
                        categories = expense.categories.toMutableList(),
                        time = expense.time
                    )
                }
            }
        } else if (suggestionId != null) {
            fetchSuggestionUseCase.getSuggestion(suggestionId).collect { suggestion ->
                // TODO Get category based on paidTo by ML or other intelligent way
                if (suggestion != null) {
                    _addExpenseViewStateFlow.value = AddExpenseViewState(
                        amount = suggestion.amount.toString(),
                        paidTo = suggestion.paidTo ?: "",
                        time = suggestion.time,
                        suggestionMessage = suggestion.referenceMessage
                    )
                }
            }
        }
    }

    suspend fun addExpense(
        expenseId: Long?,
        suggestionId: Long?,
        amount: String,
        paidTo: String?,
        categories: List<String>,
        time: Long
    ) {
        addExpenseUseCase.addExpense(
            expense = Expense(
                id = expenseId,
                amount = amount.toDouble(),
                paidTo = paidTo,
                categories = categories,
                time = time
            ),
            fromSuggestionId = suggestionId
        )
    }

    suspend fun deleteSuggestion(id: Long) {
        deleteSuggestionUseCase.deleteSuggestion(id)
    }

    suspend fun deleteExpense(id: Long) {
        deleteExpenseUseCase.deleteExpense(id)
    }
}
