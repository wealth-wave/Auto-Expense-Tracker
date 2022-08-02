package app.expense.tracker.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.expense.contract.TransactionType
import app.expense.domain.transaction.Transaction
import app.expense.presentation.viewModels.TransactionViewModel
import app.expense.presentation.viewStates.TransactionViewState
import app.expense.tracker.ui.utils.ColorGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionView(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val transactionViewState =
        viewModel.getTransactions().collectAsState(initial = TransactionViewState())

    ConstraintLayout(modifier = Modifier.padding(all = Dp(10f))) {
        val (dropDown, spent, income, balance, divider, transactions) = createRefs()
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.constrainAs(dropDown) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        ) {
            SuggestionChip(onClick = { }, enabled = true, label = {
                Text(text = "This Month", style = MaterialTheme.typography.labelMedium)
            })
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text(text = "This Month") }, onClick = { })
            }
        }
        SpentView(
            totalExpense = transactionViewState.value.expenses,
            modifier = Modifier
                .constrainAs(spent) {
                    top.linkTo(dropDown.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(.5f)
        )

        IncomeView(
            totalIncome = transactionViewState.value.incomes,
            modifier = Modifier
                .constrainAs(income) {
                    top.linkTo(spent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(balance.start)
                }
                .fillMaxWidth(.5f)
        )

        BalanceView(
            totalBalance = transactionViewState.value.incomes - transactionViewState.value.expenses,
            modifier = Modifier.constrainAs(balance) {
                top.linkTo(spent.bottom)
                start.linkTo(income.end)
                end.linkTo(parent.end)
            })

        Divider(
            modifier = Modifier
                .constrainAs(divider)
                {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(spent.bottom)
                }
                .width(2.dp)
                .height(60.dp)
        )

        RecentTransactionsView(
            transactions = transactionViewState.value.transactions,
            modifier = Modifier.constrainAs(transactions) {
                top.linkTo(spent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            })
    }
}

@Composable
private fun SpentView(totalExpense: Double, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Spent", style = MaterialTheme.typography.bodyLarge)
        Text(text = totalExpense.toString(), style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
private fun IncomeView(totalIncome: Double, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Income", style = MaterialTheme.typography.bodySmall)
        Text(text = totalIncome.toString(), style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
private fun BalanceView(totalBalance: Double, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Balance", style = MaterialTheme.typography.bodySmall)
        Text(text = totalBalance.toString(), style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
private fun RecentTransactionsView(transactions: List<Transaction>, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = "Latest Transactions", style = MaterialTheme.typography.bodyLarge)
        LazyColumn {
            items(transactions.size) { index ->
                val transaction = transactions[index]
                TransactionItemView(transaction = transaction)
            }
        }
    }
}

@Composable
private fun TransactionItemView(transaction: Transaction) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (logo, name, category, amount, type) = createRefs()
        Box(
            modifier = Modifier
                .background(
                    ColorGenerator.MATERIAL.getColor(
                        transaction.fromName ?: "OTHER"
                    ), CircleShape
                )
                .constrainAs(logo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                }
        ) {
            Text(
                text = transaction.toName?.firstOrNull()?.toString() ?: "OT",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Column(
            modifier = Modifier.constrainAs(name) {
                start.linkTo(logo.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(amount.start)
            },
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = transaction.toName ?: "Others",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Text(
            text = transaction.amount.toString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.constrainAs(amount) {
                end.linkTo(type.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })

        Box(
            modifier = Modifier
                .background(color = Color.LightGray, shape = CircleShape)
                .constrainAs(type) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }) {
            if (transaction.type == TransactionType.DEBIT) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
            } else {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
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