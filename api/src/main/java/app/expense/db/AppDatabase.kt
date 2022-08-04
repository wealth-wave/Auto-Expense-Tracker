package app.expense.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.expense.model.ExpenseDTO
import app.expense.model.SuggestionDTO

@Database(entities = [SuggestionDTO::class, ExpenseDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun suggestionDAO(): SuggestionDAO
    abstract fun expenseDAO(): ExpenseDAO
}