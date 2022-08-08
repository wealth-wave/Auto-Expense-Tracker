package app.expense.tracker.ui.views.dashboard

import android.icu.lang.UCharacter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
import java.util.Locale.getDefault

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
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

        val smsPermissionState = rememberPermissionState(
            android.Manifest.permission.READ_SMS
        )

        when (smsPermissionState.status) {
            PermissionStatus.Granted -> {
                ScreenViewContent(navController, paddingValues)
            }
            else -> {
                Column {
                    val textToShow = if (smsPermissionState.status.shouldShowRationale) {
                        "The SMS permission is important for this app. Please grant the permission."
                    } else {
                        "SMS permission required for this feature to be available. " +
                            "Please grant the permission"
                    }
                    Text(textToShow)
                    Button(onClick = { smsPermissionState.launchPermissionRequest() }) {
                        Text("Request permission")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenViewContent(
    navController: NavController,
    paddingValues: PaddingValues,
    viewModel: DashBoardViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
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
                    .padding(start = 16.dp, top = 16.dp),
                onRangeSelect = { expenseDateRange ->
                    dateRangeState.value = expenseDateRange
                }
            )

            SpentView(
                totalExpenses = dashBoardViewState.value.totalExpense,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .constrainAs(spent) {
                        top.linkTo(dateSelector.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        if (dashBoardViewState.value.suggestions.isEmpty() && dashBoardViewState.value.expenses.isEmpty()) {
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
        } else {
            LazyColumn {
                items(dashBoardViewState.value.suggestions.keys.size + dashBoardViewState.value.expenses.keys.size) { pos ->
                    val expensePos = pos - dashBoardViewState.value.suggestions.keys.size
                    if (pos < dashBoardViewState.value.suggestions.keys.size) {
                        SuggestionView(
                            pos = pos,
                            suggestionMap = dashBoardViewState.value.suggestions,
                            navController = navController,
                            onDeleteSuggestion = { id ->
                                coroutineScope.launch {
                                    viewModel.deleteSuggestion(id)
                                }
                            }
                        )
                    } else if (expensePos < dashBoardViewState.value.expenses.keys.size) {
                        ExpenseView(
                            pos = expensePos,
                            expenseMap = dashBoardViewState.value.expenses,
                            onClick = { expenseId ->
                                navController.navigate("editExpense/$expenseId")
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuggestionView(
    pos: Int,
    suggestionMap: Map<ExpenseDate, List<Suggestion>>,
    navController: NavController,
    onDeleteSuggestion: (suggestionId: Long) -> Unit
) {
    val date = suggestionMap.keys.toList()[pos]
    val suggestions = suggestionMap[date]
    Text(
        text = date.getFormattedString(),
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )

    suggestions?.forEach { suggestion ->
        Card(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 12.dp
                )
            ) {
                Text(
                    text = suggestion.referenceMessage,
                    style = MaterialTheme.typography.bodySmall
                )
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { navController.navigate("suggestExpense/${suggestion.id ?: 0}") }) {
                        Text(text = "Add To Expense")
                    }
                    TextButton(onClick = {
                        onDeleteSuggestion(suggestion.id ?: 0)
                    }) {
                        Text(text = "Ignore")
                    }
                }
            }
        }
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
private fun ExpenseView(
    pos: Int,
    expenseMap: Map<ExpenseDate, List<Expense>>,
    onClick: (expenseId: Long) -> Unit
) {
    val date = expenseMap.keys.toList()[pos]
    val expenses = expenseMap[date]

    Card(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = date.getFormattedString())
            expenses?.forEach {
                ExpenseItemView(expense = it, onClick = onClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpenseItemView(
    expense: Expense,
    onClick: (expenseId: Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .clickable(onClick = {
                onClick(expense.id ?: 0)
            }),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            modifier = Modifier
                .padding(4.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.LightGray,
                        radius = this.size.maxDimension
                    )
                },
            text = (expense.paidTo?.firstOrNull()?.titlecase() ?: "O"),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.padding(start = 20.dp))
        Column {
            Text(
                text = UCharacter.toTitleCase(
                    getDefault(),
                    expense.paidTo ?: "Unknown",
                    null,
                    0
                ),
                style = MaterialTheme.typography.titleMedium
            )
            if (expense.categories.isNotEmpty()) {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    expense.categories.forEach { category ->
                        AssistChip(
                            modifier = Modifier.padding(end = 8.dp),
                            onClick = { /*TODO*/ },
                            label = {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(text = "₹ %.2f".format(expense.amount), style = MaterialTheme.typography.bodyMedium)
    }
}

/**
 * Currently preview does not support view model provided by hilt as it expects AndroidEntryPoint
 */
@Preview
@Composable
fun PreviewDashBoardView() {
    val navController = rememberNavController()
    DashboardScreen(navController)
}
