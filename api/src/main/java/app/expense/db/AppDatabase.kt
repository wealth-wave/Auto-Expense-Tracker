package app.expense.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.expense.db.daos.CategoryDAO
import app.expense.db.daos.ExpenseDAO
import app.expense.db.daos.PaidToDAO
import app.expense.db.daos.SuggestionDAO
import app.expense.db.model.CategoryDTO
import app.expense.db.model.ExpenseDTO
import app.expense.db.model.PaidToDTO
import app.expense.db.model.SuggestionDTO

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
