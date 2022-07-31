package app.expense.di

import android.content.Context
import android.content.SharedPreferences
import app.expense.api.SMSReadAPI
import app.expense.api.TransactionSyncAPI
import app.expense.api.TransactionsReadAPI
import app.expense.db.DaoProvider
import app.expense.db.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("SESSION_PREFS", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideTransactionDao(@ApplicationContext context: Context): TransactionDao {
        return DaoProvider(context).getTransactionDao()
    }

    @Singleton
    @Provides
    fun provideTransactionSyncApi(
        sharedPreferences: SharedPreferences,
        transactionDao: TransactionDao
    ): TransactionSyncAPI {
        return TransactionSyncAPI(sharedPreferences, transactionDao)
    }

    @Singleton
    @Provides
    fun provideSmsReadApi(@ApplicationContext context: Context): SMSReadAPI {
        return SMSReadAPI(context.contentResolver)
    }

    @Singleton
    @Provides
    fun provideTransactionsReadApi(transactionDao: TransactionDao): TransactionsReadAPI {
        return TransactionsReadAPI(transactionDao)
    }
}