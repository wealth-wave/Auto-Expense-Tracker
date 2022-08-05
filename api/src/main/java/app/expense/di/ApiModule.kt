package app.expense.di

import android.content.Context
import android.content.SharedPreferences
import app.expense.api.ExpenseAPI
import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.api.SuggestionsAPI
import app.expense.db.DaoProvider
import app.expense.db.ExpenseDAO
import app.expense.db.SuggestionDAO
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
    fun provideSuggestionDao(@ApplicationContext context: Context): SuggestionDAO {
        return DaoProvider(context).suggestionDAO()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(@ApplicationContext context: Context): ExpenseDAO {
        return DaoProvider(context).expenseDAO()
    }

    @Singleton
    @Provides
    fun provideSuggestionSyncApi(
        sharedPreferences: SharedPreferences,
        suggestionDao: SuggestionDAO
    ): SuggestionSyncAPI {
        return SuggestionSyncAPI(sharedPreferences, suggestionDao)
    }

    @Singleton
    @Provides
    fun provideSmsReadApi(@ApplicationContext context: Context): SMSReadAPI {
        return SMSReadAPI(context.contentResolver)
    }

    @Singleton
    @Provides
    fun provideSuggestionsApi(suggestionDao: SuggestionDAO): SuggestionsAPI {
        return SuggestionsAPI(suggestionDao)
    }

    @Singleton
    @Provides
    fun provideExpenseApi(expenseDAO: ExpenseDAO): ExpenseAPI {
        return ExpenseAPI(expenseDAO)
    }
}