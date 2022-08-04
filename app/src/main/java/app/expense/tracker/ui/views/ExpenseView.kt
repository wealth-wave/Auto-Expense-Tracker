package app.expense.tracker.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.expense.domain.suggestion.Suggestion
import app.expense.presentation.viewModels.ExpenseViewModel
import app.expense.presentation.viewStates.SuggestionViewState
import app.expense.tracker.ui.utils.ColorGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseView(
    navController: NavController,
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    val suggestionViewState =
        viewModel.getSuggestions().collectAsState(initial = SuggestionViewState())

    ConstraintLayout(modifier = Modifier.padding(all = Dp(10f))) {
        val (dropDown, spent, income, balance, divider, transText, expenses) = createRefs()
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
            totalExpense = suggestionViewState.value.expenses,
            modifier = Modifier
                .constrainAs(spent) {
                    top.linkTo(dropDown.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(.5f)
        )



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
        Text(text = "Latest Expenses", modifier = Modifier.constrainAs(transText) {
            top.linkTo(income.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, style = MaterialTheme.typography.bodyLarge)
        LazyColumn(modifier = Modifier
            .constrainAs(expenses) {
                top.linkTo(transText.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
            .fillMaxHeight(0.7f)) {
            items(suggestionViewState.value.suggestions.size) { index ->
                val suggestion = suggestionViewState.value.suggestions[index]
                ExpenseItemView(suggestion = suggestion)
            }
        }
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
private fun ExpenseItemView(suggestion: Suggestion) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 1.dp)
    ) {
        val (logo, name, category, amount, type) = createRefs()
        Box(
            modifier = Modifier
                .background(
                    ColorGenerator.MATERIAL.getColor(
                        suggestion.toName ?: "OTHER"
                    ), CircleShape
                )
                .constrainAs(logo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                }
        ) {
            Text(
                text = suggestion.toName?.firstOrNull()?.toString() ?: "OT",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(all = 10.dp)
            )
        }

        Column(
            modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(logo.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = suggestion.toName ?: "Others",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Text(
            text = suggestion.amount.toString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .constrainAs(amount) {
                    end.linkTo(type.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(end = 10.dp)
        )
    }
}

/**
 * Currently preview does not support viewmodel provided by hilt as it expects AndroidEntryPoint
 */
@Preview
@Composable
fun PreviewExpenseView() {
    val navController = rememberNavController()
    ExpenseView(navController)
}