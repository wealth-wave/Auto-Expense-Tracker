package app.expense.di

import android.content.Context
import android.content.SharedPreferences
import app.expense.api.CategoryAPI
import app.expense.api.ExpenseAPI
import app.expense.api.PaidToAPI
import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.api.SuggestionsAPI
import app.expense.db.CategoryDAO
import app.expense.db.DaoProvider
import app.expense.db.ExpenseDAO
import app.expense.db.PaidToDAO
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
    fun provideDaoProvider(@ApplicationContext context: Context): DaoProvider {
        return DaoProvider(context)
    }

    @Singleton
    @Provides
    fun provideSuggestionDao(daoProvider: DaoProvider): SuggestionDAO {
        return daoProvider.suggestionDAO()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(daoProvider: DaoProvider): ExpenseDAO {
        return daoProvider.expenseDAO()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(daoProvider: DaoProvider): CategoryDAO {
        return daoProvider.categoryDAO()
    }

    @Singleton
    @Provides
    fun providePaidToDao(daoProvider: DaoProvider): PaidToDAO {
        return daoProvider.paidToDAO()
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

    @Singleton
    @Provides
    fun provideCategoryApi(categoryDAO: CategoryDAO): CategoryAPI {
        return CategoryAPI(categoryDAO)
    }

    @Singleton
    @Provides
    fun providePaidToApi(paidToDAO: PaidToDAO): PaidToAPI {
        return PaidToAPI(paidToDAO)
    }
}
