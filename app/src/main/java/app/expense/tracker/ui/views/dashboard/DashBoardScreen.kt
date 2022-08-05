package app.expense.tracker.ui.views.dashboard

import DateRangeSelectorView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.expense.domain.expense.Expense
import app.expense.domain.suggestion.Suggestion
import app.expense.presentation.viewModels.DashBoardViewModel
import app.expense.presentation.viewStates.DashBoardViewState
import app.expense.presentation.viewStates.DateRange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashBoardViewModel = hiltViewModel()
) {
    val dateRangeState =
        remember { mutableStateOf<DateRange>(DateRange.ThisMonth) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarScrollState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = {
                    DateRangeSelectorView(onRangeSelect = { expenseDateRange ->
                        dateRangeState.value = expenseDateRange
                    })
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                icons = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        containerColor = BottomAppBarDefaults.FloatingActionButtonContainerColor,
                        elevation = BottomAppBarDefaults.FloatingActionButtonElevation
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Expense")
                    }
                }
            )
        },
    ) { paddingValues ->
        ScreenViewContent(dateRangeState.value, paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenViewContent(
    expenseDateRange: DateRange,
    paddingValues: PaddingValues,
    viewModel: DashBoardViewModel = viewModel()
) {
    val dashBoardViewState = viewModel.getDashBoardViewState(expenseDateRange)
        .collectAsState(initial = DashBoardViewState())

    ConstraintLayout(modifier = Modifier.padding(paddingValues)) {
        val (spent, suggestions, expenses) = createRefs()

        SpentView(
            totalExpenses = dashBoardViewState.value.totalExpense,
            modifier = Modifier
                .padding(all = 16.dp)
                .constrainAs(spent) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        if (dashBoardViewState.value.suggestions.isNotEmpty()) {
            SuggestionsView(
                suggestions = dashBoardViewState.value.suggestions,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .constrainAs(suggestions) {
                        top.linkTo(spent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
        val ref = if (dashBoardViewState.value.suggestions.isNotEmpty()) {
            suggestions
        } else {
            spent
        }
        ExpensesView(expenses = dashBoardViewState.value.expenses, modifier = Modifier
            .padding(all = 16.dp)
            .constrainAs(expenses) {
                top.linkTo(ref.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

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

@Composable
private fun SuggestionsView(suggestions: List<Suggestion>, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding()
    ) {

        val expandedState = remember { mutableStateOf(false) }

        TextButton(
            onClick = {
                expandedState.value = !expandedState.value
            },
        ) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = "Expense suggestions")
            Text(text = "Detected Expenses", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Suggestion Header")
        }
        Divider(modifier = Modifier.background(color = MaterialTheme.colorScheme.background))
        if (expandedState.value) {
            LazyColumn() {
                items(suggestions.size) { pos ->
                    SuggestionItemView(suggestion = suggestions[pos])
                }
            }
        }
    }
}


@Composable
private fun ExpensesView(expenses: List<Expense>, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding()
    ) {


        Row() {
            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Expenses")
            Text(text = "Expenses", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.weight(1f))
        }
        Divider(modifier = Modifier.background(color = MaterialTheme.colorScheme.background))
        if (expenses.isNotEmpty()) {
            LazyColumn() {
                items(expenses.size) { pos ->
                    ExpenseItemView(expense = expenses[pos])
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
private fun SuggestionItemView(suggestion: Suggestion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = suggestion.paidTo ?: "Unknown", style = MaterialTheme.typography.bodyMedium)
        Text(text = "₹ %.2f".format(suggestion.amount), style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Suggestion")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Add Suggestion")
        }
    }
}

@Composable
private fun ExpenseItemView(expense: Expense) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = expense.paidTo ?: "Unknown", style = MaterialTheme.typography.bodyMedium)
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
    val viewModel: DashBoardViewModel = viewModel()
    DashboardScreen(navController, viewModel)
}