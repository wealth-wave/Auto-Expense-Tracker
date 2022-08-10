package app.expense.tracker.ui.views.home

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.expense.presentation.viewModels.HomeScreenViewModel
import app.expense.tracker.ui.utils.ScreenRoute
import app.expense.tracker.ui.utils.replace
import app.expense.tracker.ui.views.expense.ExpenseScreen
import app.expense.tracker.ui.views.suggestions.SuggestionsScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    onAddExpense: () -> Unit,
    onEditExpense: (expenseId: Long) -> Unit,
    onAddSuggestion: (suggestionId: Long) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navController = rememberNavController()
    val currentSelectedRoute = remember { mutableStateOf(ScreenRoute.Expense.TEMPLATE) }
    val smsPermissionState = rememberPermissionState(Manifest.permission.READ_SMS)
    val suggestionCount = viewModel.getSuggestionsCount().collectAsState(initial = 0).value
    val badgeCount = if (smsPermissionState.status != PermissionStatus.Granted) {
        1
    } else {
        suggestionCount
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddExpense() },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Expense")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    selected = currentSelectedRoute.value == ScreenRoute.Expense.TEMPLATE,
                    onClick = {
                        currentSelectedRoute.value = ScreenRoute.Expense.TEMPLATE
                        navController.replace(currentSelectedRoute.value)
                    }
                )
                NavigationBarItem(
                    icon = {
                        BadgedBox(badge = { Text(text = badgeCount.toString()) }) {
                            Icon(Icons.Filled.Notifications, contentDescription = null)
                        }
                    },
                    selected = currentSelectedRoute.value == ScreenRoute.Suggestions.TEMPLATE,
                    onClick = {
                        currentSelectedRoute.value = ScreenRoute.Suggestions.TEMPLATE
                        navController.replace(currentSelectedRoute.value)
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            navController = navController,
            startDestination = ScreenRoute.Expense.TEMPLATE,
        ) {
            composable(ScreenRoute.Expense.TEMPLATE) { ExpenseScreen(onEditExpense = onEditExpense) }
            composable(ScreenRoute.Suggestions.TEMPLATE) { SuggestionsScreen(onAddSuggestion = onAddSuggestion) }
        }
    }
}

/**
 * Currently preview does not support view model provided by hilt as it expects AndroidEntryPoint
 */
@Preview
@Composable
fun PreviewDashBoardView() {
    HomeScreen({}, {}, {})
}
