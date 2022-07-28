package app.expense.api

import android.content.SharedPreferences
import app.expense.db.TransactionDao
import app.expense.model.TransactionDTO

class TransactionSyncAPI(
    private val sharedPreferences: SharedPreferences,
    private val transactionDao: TransactionDao
) {

    fun getLastSyncedTime(): Long? {
        val lastSyncTime = sharedPreferences.getLong(LAST_SYNC_TIME_KEY, -1L)
        if (lastSyncTime == -1L) {
            return null
        }

        return lastSyncTime
    }

    suspend fun storeTransactions(transactions: List<TransactionDTO>) {
        transactionDao.insertAll(transactions)
    }

    fun setLastSyncedTime(time: Long) {
        sharedPreferences.edit().putLong(LAST_SYNC_TIME_KEY, time).apply()
    }

    companion object {
        private const val LAST_SYNC_TIME_KEY = "LAST_SYNC_TIME"
    }
}