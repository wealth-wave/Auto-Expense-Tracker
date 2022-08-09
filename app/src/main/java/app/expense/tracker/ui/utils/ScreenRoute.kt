package app.expense.tracker.ui.utils

object ScreenRoute {

    object Home {
        const val ROUTE = "home"
    }

    object Expense {
        const val ROUTE = "expense"
    }

    object Suggestions {
        const val ROUTE = "home/suggestions"
    }

    object AddExpense {
        const val ROUTE = "add_expense"
    }

    object SuggestExpense {
        const val ROUTE = "suggest_expense/{suggestion_id}"
        const val SUGGESTION_ID_ARG = "suggestion_id"

        fun getSuggestExpense(suggestionId: Long) = "suggest_expense/$suggestionId"
    }

    object EditExpense {
        const val ROUTE = "edit_expense/{expense_id}"
        const val EXPENSE_ID_ARG = "expense_id"

        fun getEditExpense(expenseId: Long) = "edit_expense/$expenseId"
    }
}