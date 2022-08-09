package app.expense.presentation.viewStates

data class SuggestionListState(
    val dateSuggestionsMap: Map<String, List<Item>> = emptyMap()
) {
    data class Item(
        val id: Long,
        val amount: String,
        val message: String,
    )
}
