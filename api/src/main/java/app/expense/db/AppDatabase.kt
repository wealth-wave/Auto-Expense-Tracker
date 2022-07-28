package app.expense.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.expense.model.TransactionDTO

@Database(entities = [TransactionDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}