package app.expense.db

import android.content.Context
import androidx.room.Room

class DaoProvider(context: Context) {
    private val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "expense_db").build()
    }

    fun getTransactionDao() = db.transactionDao()
}