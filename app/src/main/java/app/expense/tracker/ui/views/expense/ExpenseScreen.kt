package app.expense.tracker.ui.views.expense

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpenseScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ExpenseStatsView(modifier = modifier)
    }

}