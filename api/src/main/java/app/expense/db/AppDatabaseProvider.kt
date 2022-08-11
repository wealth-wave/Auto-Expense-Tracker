package app.expense.db

import android.content.Context
import androidx.room.Room

class AppDatabaseProvider(private val context: Context) {

    fun getDatabase(): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "expense_db")
            .build()
    }
}
