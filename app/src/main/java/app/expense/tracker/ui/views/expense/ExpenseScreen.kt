package app.expense.tracker.ui.views.expense

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun ExpenseScreen(onEditExpense: (expenseId: Long) -> Unit) {
    Column {
        ExpenseStatsView()
        ExpenseListView(onEditExpense = onEditExpense)
    }
}
