package app.expense.domain.mappers

import app.expense.domain.expense.Expense
import app.expense.domain.suggestion.Suggestion
import app.expense.model.ExpenseDTO
import app.expense.model.SuggestionDTO

class DataMapper {

    fun mapToSuggestion(suggestion: SuggestionDTO) = Suggestion(
        id = suggestion.id,
        amount = suggestion.amount,
        paidTo = suggestion.paidTo,
        time = suggestion.time,
        referenceMessage = suggestion.referenceMessage,
        referenceMessageSender = suggestion.referenceMessageSender
    )

    fun mapToExpense(expense: ExpenseDTO) = Expense(
        id = expense.id,
        amount = expense.amount,
        paidTo = expense.paidTo,
        categories = expense.categories,
        time = expense.time
    )
}