package app.expense.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.expense.model.CategoryDTO
import app.expense.model.ExpenseDTO
import app.expense.model.PaidToDTO
import app.expense.model.SuggestionDTO

@Database(
    entities = [SuggestionDTO::class, ExpenseDTO::class, CategoryDTO::class, PaidToDTO::class],
    version = 1
)
@TypeConverters(ModelConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun suggestionDAO(): SuggestionDAO
    abstract fun expenseDAO(): ExpenseDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun paidToDAO(): PaidToDAO
}
