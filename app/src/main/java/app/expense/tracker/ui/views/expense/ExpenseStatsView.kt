package app.expense.tracker.ui.views.expense

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.expense.presentation.viewModels.ExpenseStatsViewModel
import app.expense.presentation.viewStates.ExpenseStats
import app.expense.tracker.R
import java.text.NumberFormat

@Composable
fun ExpenseStatsView(
    viewModel: ExpenseStatsViewModel = hiltViewModel()
) {

    val expenseStats =
        viewModel.getExpenseStats().collectAsState(initial = ExpenseStats()).value
    Card(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.default_padding))
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(dimensionResource(R.dimen.default_padding))) {
            if (expenseStats.monthlySpent.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_expenses),
                    style = MaterialTheme.typography.displaySmall
                )
            } else {
                val lastMonthName = expenseStats.monthlySpent.keys.last()
                val lastMonthExpense = expenseStats.monthlySpent.values.last()

                Column {
                    Text(
                        text = stringResource(id = R.string.net_spend, lastMonthName),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.small_gap)))
                    Text(
                        text = NumberFormat.getCurrencyInstance().format(lastMonthExpense),
                        style = MaterialTheme.typography.displayMedium
                    )
                }

            }
        }

    }
}