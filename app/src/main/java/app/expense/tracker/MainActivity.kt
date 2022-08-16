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
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import app.expense.tracker.services.SMSSyncWorker
import app.expense.tracker.services.SuggestionDetectionWorker
import app.expense.tracker.ui.theme.AutoExpenseTrackerTheme
import app.expense.tracker.ui.utils.ScreenRoute
import app.expense.tracker.ui.views.addExpense.AddExpenseScreen
import app.expense.tracker.ui.views.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration

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
                        startDestination = ScreenRoute.Home.TEMPLATE,
                    ) {
                        composable(ScreenRoute.Home.TEMPLATE) {
                            HomeScreen(
                                onAddExpense = { navController.navigate(ScreenRoute.AddExpense.TEMPLATE) },
                                onEditExpense = { expenseId ->
                                    navController.navigate(
                                        ScreenRoute.EditExpense.getEditExpenseRoute(
                                            expenseId
                                        )
                                    )
                                },
                                onAddSuggestion = { suggestionId ->
                                    navController.navigate(
                                        ScreenRoute.SuggestExpense.getSuggestExpenseRoute(
                                            suggestionId
                                        )
                                    )
                                }
                            )
                        }
                        composable(ScreenRoute.AddExpense.TEMPLATE) {
                            AddExpenseScreen(onGoBack = {
                                navController.popBackStack()
                            })
                        }
                        composable(
                            route = ScreenRoute.EditExpense.TEMPLATE,
                            arguments = listOf(
                                navArgument(ScreenRoute.EditExpense.EXPENSE_ID_ARG) {
                                    type = NavType.LongType
                                }
                            )
                        ) { navBackStackEntry ->
                            AddExpenseScreen(
                                onGoBack = {
                                    navController.popBackStack()
                                },
                                expenseId = navBackStackEntry.arguments?.getLong(ScreenRoute.EditExpense.EXPENSE_ID_ARG)
                            )
                        }
                        composable(
                            route = ScreenRoute.SuggestExpense.TEMPLATE,
                            arguments = listOf(
                                navArgument(ScreenRoute.SuggestExpense.SUGGESTION_ID_ARG) {
                                    type = NavType.LongType
                                }
                            )
                        ) { backStackEntry ->
                            AddExpenseScreen(
                                onGoBack = {
                                    navController.popBackStack()
                                },
                                suggestionId = backStackEntry.arguments?.getLong(ScreenRoute.SuggestExpense.SUGGESTION_ID_ARG)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun syncSMS() {
        val workManager =
            WorkManager.getInstance(this)

        workManager.enqueue(
            PeriodicWorkRequest.Builder(
                SMSSyncWorker::class.java,
                Duration.ofMinutes(15),
                Duration.ofMinutes(5)
            ).build()
        )
        workManager.enqueue(
            PeriodicWorkRequest.Builder(
                SuggestionDetectionWorker::class.java,
                Duration.ofMinutes(15),
                Duration.ofMinutes(5)
            ).build()
        )
    }
}
