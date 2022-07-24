package app.expense.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.expense.model.ExpenseDTO

@Database(entities = [ExpenseDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}