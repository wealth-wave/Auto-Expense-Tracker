package app.expense.tracker.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.expense.presentation.viewModels.TransactionViewModel
import app.expense.presentation.viewStates.TransactionViewState

@Composable
fun TransactionView(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val transactionViewState =
        viewModel.getTransactions().collectAsState(initial = TransactionViewState())
    Column {
        Row(modifier = Modifier.padding(all = Dp(10f))) {
            Text(text = transactionViewState.value.incomes.toString())
            Text(text = "  ")
            Text(text = transactionViewState.value.expenses.toString())
        }
        LazyColumn {
            val transactions = transactionViewState.value.transactions
            items(transactions.size) { index ->
                val transaction = transactions[index]
                Row {
                    Text(text = transaction.fromName ?: "")
                    Text(text = "  ")
                    Text(text = transaction.toName ?: "")
                    Text(text = "  ")
                    Text(text = transaction.amount.toString())
                }
            }
        }
    }

}

/**
 * Currently preview does not support viewmodel provided by hilt as it expects AndroidEntryPoint
 */
@Preview
@Composable
fun PreviewTransactionView() {
    val navController = rememberNavController()
    TransactionView(navController)
}