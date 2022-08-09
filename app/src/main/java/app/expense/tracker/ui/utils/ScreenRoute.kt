package app.expense.tracker.ui.utils

object ScreenRoute {

    object Home {
        const val TEMPLATE = "home"
    }

    object Expense {
        const val TEMPLATE = "expense"
    }

    object Suggestions {
        const val TEMPLATE = "suggestions"
    }

    object AddExpense {
        const val TEMPLATE = "expense/new"
    }

    object SuggestExpense {
        const val TEMPLATE = "suggest_expense/{suggestion_id}"
        const val SUGGESTION_ID_ARG = "suggestion_id"

        fun getSuggestExpenseRoute(suggestionId: Long) = "suggest_expense/$suggestionId"
    }

    object EditExpense {
        const val TEMPLATE = "edit_expense/{expense_id}"
        const val EXPENSE_ID_ARG = "expense_id"

        fun getEditExpenseRoute(expenseId: Long) = "edit_expense/$expenseId"
    }
}