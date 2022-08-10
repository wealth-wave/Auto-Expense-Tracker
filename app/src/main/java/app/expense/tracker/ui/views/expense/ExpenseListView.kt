package app.expense.tracker.ui.views.expense

import android.icu.lang.UCharacter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import app.expense.presentation.viewModels.ExpenseListViewModel
import app.expense.presentation.viewStates.ExpenseListState
import app.expense.tracker.R
import java.util.Locale.getDefault

@Composable
fun ExpenseListView(
    onEditExpense: (expenseId: Long) -> Unit,
    viewModel: ExpenseListViewModel = hiltViewModel()
) {
    val expenseListState =
        viewModel.getExpenseListState().collectAsState(initial = ExpenseListState()).value

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
        Text(
            text = stringResource(R.string.transactions),
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn {
            items(expenseListState.dateExpenseMap.size) { pos ->
                val dateString = expenseListState.dateExpenseMap.keys.toList()[pos]
                val expenseItems = expenseListState.dateExpenseMap[dateString]
                Card(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.large_gap),
                            bottom = dimensionResource(id = R.dimen.small_gap)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.default_padding),
                            end = dimensionResource(id = R.dimen.default_padding),
                            top = dimensionResource(id = R.dimen.default_padding),
                        )
                    ) {
                        Text(
                            text = dateString,
                            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small_gap)),
                            style = MaterialTheme.typography.labelLarge
                        )
                        expenseItems?.forEach { expenseItem ->

                            Row(
                                modifier = Modifier
                                    .padding(dimensionResource(id = R.dimen.default_padding))
                                    .clickable(onClick = {
                                        onEditExpense(expenseItem.id)
                                    })
                            ) {
                                CircleTextLogo(expenseItem)
                                Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.default_padding)))
                                Text(
                                    text = getFormattedPaidTo(expenseItem),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = expenseItem.amount)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun getFormattedPaidTo(expenseItem: ExpenseListState.Item) =
    UCharacter.toTitleCase(
        getDefault(),
        expenseItem.paidTo ?: stringResource(R.string.unknown_paid_to),
        null,
        0
    )

@Composable
private fun CircleTextLogo(
    expenseItem: ExpenseListState.Item
) {
    val color = MaterialTheme.colorScheme.inversePrimary
    Text(
        modifier = Modifier
            .drawBehind {
                drawCircle(
                    color = color,
                    radius = this.size.minDimension.times(1.2f)
                )
            },
        text = (expenseItem.paidTo?.firstOrNull()?.titlecase() ?: "O"),
        style = MaterialTheme.typography.bodyLarge
    )
}
