package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.domain.expense.FetchExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val fetchExpenseUseCase: FetchExpenseUseCase
) : ViewModel() {


}