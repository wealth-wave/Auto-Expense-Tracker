package app.expense.api

import android.content.SharedPreferences
import app.expense.db.ExpenseDao
import app.expense.model.ExpenseDTO

class ExpenseSyncAPI(
    private val sharedPreferences: SharedPreferences,
    private val expenseDao: ExpenseDao
) {

    fun getLastSyncedTime(): Long? {
        val lastSyncTime = sharedPreferences.getLong(LAST_SYNC_TIME_KEY, -1L)
        if (lastSyncTime == -1L) {
            return null
        }

        return lastSyncTime
    }

    suspend fun storeExpenses(expenses: List<ExpenseDTO>) {
        expenseDao.insertAll(expenses)
    }

    fun setLastSyncedTime(time: Long) {
        sharedPreferences.edit().putLong(LAST_SYNC_TIME_KEY, time).apply()
    }

    companion object {
        private const val LAST_SYNC_TIME_KEY = "LAST_SYNC_TIME"
    }
}