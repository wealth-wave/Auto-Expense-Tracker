package app.expense.presentation.viewStates

import app.expense.domain.suggestion.Suggestion

data class SuggestionViewState(
    val expenses: Double = 0.0,
    val suggestions: List<Suggestion> = emptyList()
)