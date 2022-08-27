package app.expense.tracker.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import app.expense.tracker.ui.utils.ScreenRoute
import app.expense.tracker.ui.views.addExpense.AddExpenseScreen
import app.expense.tracker.ui.views.home.HomeScreen

@Composable
fun HomeNavigation() {
    val navController = rememberNavController()
    val deepLinkUri = "expense://"
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.TEMPLATE,
    ) {
        composable(
            route = ScreenRoute.Home.TEMPLATE,
            deepLinks = listOf(navDeepLink { uriPattern = deepLinkUri + ScreenRoute.Home.TEMPLATE })
        ) {
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
