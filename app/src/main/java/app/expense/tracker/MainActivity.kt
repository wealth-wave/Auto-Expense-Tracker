package app.expense.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import app.expense.tracker.services.SMSSyncWorker
import app.expense.tracker.ui.theme.AutoExpenseTrackerTheme
import app.expense.tracker.ui.utils.ScreenRoute
import app.expense.tracker.ui.views.expense.AddExpenseScreen
import app.expense.tracker.ui.views.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachUI()
        syncSMS()
    }

    private fun attachUI() {
        setContent {
            AutoExpenseTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.Home.ROUTE,
                    ) {
                        composable(ScreenRoute.Home.ROUTE) {
                            HomeScreen(onAddExpenseClicked = {
                                navController.navigate(ScreenRoute.AddExpense.ROUTE)
                            })
                        }
                        composable(ScreenRoute.AddExpense.ROUTE) { AddExpenseScreen(navController) }
                        composable(
                            route = ScreenRoute.EditExpense.ROUTE,
                            arguments = listOf(
                                navArgument(ScreenRoute.EditExpense.EXPENSE_ID_ARG) {
                                    type = NavType.LongType
                                }
                            )
                        ) { navBackStackEntry ->
                            AddExpenseScreen(
                                navController,
                                expenseId = navBackStackEntry.arguments?.getLong(ScreenRoute.EditExpense.EXPENSE_ID_ARG)
                            )
                        }
                        composable(
                            route = ScreenRoute.SuggestExpense.ROUTE,
                            arguments = listOf(
                                navArgument(ScreenRoute.SuggestExpense.SUGGESTION_ID_ARG) {
                                    type = NavType.LongType
                                }
                            )
                        ) { backStackEntry ->
                            AddExpenseScreen(
                                navController = navController,
                                suggestionId = backStackEntry.arguments?.getLong(ScreenRoute.SuggestExpense.SUGGESTION_ID_ARG)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun syncSMS() {
        val smsSyncRequest = OneTimeWorkRequestBuilder<SMSSyncWorker>()
            .build()
        WorkManager.getInstance(this).enqueue(smsSyncRequest)
    }
}
