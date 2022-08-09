package app.expense.tracker.ui.views.expense

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun ExpenseScreen() {
    Column {
        ExpenseStatsView()
        ExpenseListView()
    }

}