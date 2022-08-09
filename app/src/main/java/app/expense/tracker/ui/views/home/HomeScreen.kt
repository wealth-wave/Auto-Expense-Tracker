package app.expense.tracker.ui.views.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.expense.tracker.ui.utils.ScreenRoute
import app.expense.tracker.ui.utils.replace
import app.expense.tracker.ui.views.expense.ExpenseScreen
import app.expense.tracker.ui.views.suggestions.SuggestionsScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    onAddExpenseClicked: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navController = rememberNavController()
    val currentSelectedRoute = remember { mutableStateOf(ScreenRoute.Expense.ROUTE) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddExpenseClicked() },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Expense")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    selected = currentSelectedRoute.value == ScreenRoute.Expense.ROUTE,
                    onClick = {
                        currentSelectedRoute.value = ScreenRoute.Expense.ROUTE
                        navController.replace(currentSelectedRoute.value)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Notifications, contentDescription = null) },
                    selected = currentSelectedRoute.value == ScreenRoute.Suggestions.ROUTE,
                    onClick = {
                        currentSelectedRoute.value = ScreenRoute.Suggestions.ROUTE
                        navController.replace(currentSelectedRoute.value)
                    }
                )
            }
        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)

        NavHost(
            modifier = modifier.fillMaxSize(),
            navController = navController,
            startDestination = ScreenRoute.Expense.ROUTE,
        ) {
            composable(ScreenRoute.Expense.ROUTE) { ExpenseScreen(modifier = modifier) }
            composable(ScreenRoute.Suggestions.ROUTE) { SuggestionsScreen() }
        }
    }
}


/**
 * Currently preview does not support view model provided by hilt as it expects AndroidEntryPoint
 */
@Preview
@Composable
fun PreviewDashBoardView() {
    HomeScreen {}
}
