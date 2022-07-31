package app.expense.tracker.ui.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
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
    Text(text = transactionViewState.value.expenses.toString())
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