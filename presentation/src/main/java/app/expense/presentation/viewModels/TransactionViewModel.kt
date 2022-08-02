package app.expense.presentation.viewModels

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import app.expense.contract.TransactionType
import app.expense.domain.TransactionFetchService
import app.expense.presentation.viewStates.TransactionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val transactionFetchService: TransactionFetchService) :
    ViewModel() {

    fun getTransactions(): Flow<TransactionViewState> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val upTo = calendar.timeInMillis
        return transactionFetchService.getTransactions(upTo).map { transactions ->
            val expenses =
                transactions.filter { it.type == TransactionType.DEBIT }.sumOf { it.amount }
            val incomes =
                transactions.filter { it.type == TransactionType.CREDIT }.sumOf { it.amount }
            TransactionViewState(
                expenses = expenses,
                incomes = incomes,
                transactions = transactions
            )
        }
    }
}