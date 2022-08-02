package app.expense.presentation.viewModels

import androidx.lifecycle.ViewModel
import app.expense.contract.TransactionType
import app.expense.domain.TransactionFetchService
import app.expense.presentation.viewStates.TransactionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val transactionFetchService: TransactionFetchService) :
    ViewModel() {

    fun getTransactions(): Flow<TransactionViewState> {
        val time = System.currentTimeMillis()
        val upTo = time - TimeUnit.DAYS.toMillis(30)
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