package app.expense.tracker.ui.views.dashboard

import DateRangeSelectorView
import android.icu.lang.UCharacter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.expense.domain.expense.Expense
import app.expense.domain.suggestion.Suggestion
import app.expense.presentation.viewModels.DashBoardViewModel
import app.expense.presentation.viewStates.DashBoardViewState
import app.expense.presentation.viewStates.DateRange
import app.expense.presentation.viewStates.ExpenseDate
import app.expense.tracker.ui.utils.ColorGenerator
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController
) {


    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarScrollState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            BottomAppBar(
                icons = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate("addExpense") },
                        containerColor = BottomAppBarDefaults.FloatingActionButtonContainerColor,
                        elevation = BottomAppBarDefaults.FloatingActionButtonElevation
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Expense")
                    }
                }
            )
        },
    ) { paddingValues ->
        ScreenViewContent(navController, paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenViewContent(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: DashBoardViewModel = hiltViewModel()
) {

    val dateRangeState =
        remember { mutableStateOf<DateRange>(DateRange.ThisMonth) }
    val dashBoardViewState = viewModel.getDashBoardViewState(dateRangeState.value)
        .collectAsState(initial = DashBoardViewState())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)

        ) {
            val (dateSelector, spent) = createRefs()

            DateRangeSelectorView(
                modifier = Modifier
                    .constrainAs(dateSelector) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .padding(16.dp),
                onRangeSelect = { expenseDateRange ->
                    dateRangeState.value = expenseDateRange
                })

            SpentView(
                totalExpenses = dashBoardViewState.value.totalExpense,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .constrainAs(spent) {
                        top.linkTo(dateSelector.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }

        if (dashBoardViewState.value.suggestions.isNotEmpty()) {
            SuggestionsView(
                navController = navController,
                suggestionMap = dashBoardViewState.value.suggestions,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            )
        }
        ExpensesView(
            navController = navController,
            expenseMap = dashBoardViewState.value.expenses,
            modifier = Modifier
                .padding(all = 16.dp)
                .background(color = MaterialTheme.colorScheme.background)
        )
    }
}

@Composable
private fun SpentView(totalExpenses: Double, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Total Spent", style = MaterialTheme.typography.titleLarge)
        Text(text = "₹ %.2f".format(totalExpenses), style = MaterialTheme.typography.headlineSmall)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuggestionsView(
    navController: NavController,
    suggestionMap: Map<ExpenseDate, List<Suggestion>>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding()
    ) {
        LazyColumn() {
            items(suggestionMap.keys.size) { pos ->
                val date = suggestionMap.keys.toList()[pos]
                val suggestions = suggestionMap[date]

                Card(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = date.getFormattedString())
                        suggestions?.forEach {
                            SuggestionItemView(navController = navController, suggestion = it)
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpensesView(
    navController: NavController,
    expenseMap: Map<ExpenseDate, List<Expense>>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding()
    ) {
        if (expenseMap.isNotEmpty()) {

            LazyColumn() {
                items(expenseMap.keys.size) { pos ->
                    val date = expenseMap.keys.toList()[pos]
                    val expenses = expenseMap[date]

                    Card(
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = date.getFormattedString())
                            expenses?.forEach {
                                ExpenseItemView(navController = navController, expense = it)
                            }
                        }
                    }

                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(imageVector = Icons.Filled.Face, contentDescription = "Empty expenses")
                Text(text = "Hooray, No Expenses", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun SuggestionItemView(navController: NavController, suggestion: Suggestion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .clickable(onClick = {
                navController.navigate("suggestExpense/${suggestion.id ?: 0}")
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = ColorGenerator.MATERIAL.getColor(
                        suggestion.paidTo ?: "U"
                    ),
                    shape = RoundedCornerShape(100)
                )
                .padding(8.dp)
        ) {
            Text(
                text = (suggestion.paidTo?.firstOrNull()?.titlecase() ?: "O"),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.padding(start = 16.dp))
        Column {
            Text(
                text = UCharacter.toTitleCase(
                    Locale.getDefault(),
                    suggestion.paidTo ?: "Unknown",
                    null,
                    0
                ), style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(text = "₹ %.2f".format(suggestion.amount), style = MaterialTheme.typography.bodyLarge)
        Icon(imageVector = Icons.Filled.Check, contentDescription = "Check")
    }
}

@Composable
private fun ExpenseItemView(
    navController: NavController, expense: Expense
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .clickable(onClick = {
                navController.navigate("editExpense/${expense.id ?: 0}")
            }),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Box(
            modifier = Modifier
                .background(
                    color = ColorGenerator.MATERIAL.getColor(
                        expense.paidTo ?: "U"
                    ),
                    shape = RoundedCornerShape(100)
                )
                .padding(8.dp)
        ) {
            Text(
                text = (expense.paidTo?.firstOrNull()?.titlecase() ?: "O"),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.padding(start = 16.dp))
        Column {
            Text(
                text = UCharacter.toTitleCase(
                    Locale.getDefault(),
                    expense.paidTo ?: "Unknown",
                    null,
                    0
                ), style = MaterialTheme.typography.bodyMedium
            )
            if (expense.categories.isNotEmpty()) {
                LazyRow() {
                    items(expense.categories.size) { pos ->
                        Text(
                            text = expense.categories[pos], modifier = Modifier.background(
                                color = ColorGenerator.MATERIAL.getColor(expense.categories[pos]),
                                shape = RoundedCornerShape(100)
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(text = "₹ %.2f".format(expense.amount), style = MaterialTheme.typography.bodyLarge)
    }
}

/**
 * Currently preview does not support viewmodel provided by hilt as it expects AndroidEntryPoint
 */
@Preview
@Composable
fun PreviewDashBoardView() {
    val navController = rememberNavController()
    DashboardScreen(navController)
}