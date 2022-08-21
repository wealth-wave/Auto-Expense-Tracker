package app.expense.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.expense.api.CategoryAPI
import app.expense.api.ExpenseAPI
import app.expense.api.PaidToAPI
import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.api.SuggestionsAPI
import app.expense.db.AppDatabase
import app.expense.db.AppDatabaseProvider
import app.expense.db.daos.CategoryDAO
import app.expense.db.daos.ExpenseDAO
import app.expense.db.daos.PaidToDAO
import app.expense.db.daos.SuggestionDAO
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
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create { context.dataStoreFile("pref.preferences_pb") }
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabaseProvider(context).getDatabase()
    }

    @Singleton
    @Provides
    fun provideSuggestionDao(appDatabase: AppDatabase): SuggestionDAO {
        return appDatabase.suggestionDAO()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDAO {
        return appDatabase.expenseDAO()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDAO {
        return appDatabase.categoryDAO()
    }

    @Singleton
    @Provides
    fun providePaidToDao(appDatabase: AppDatabase): PaidToDAO {
        return appDatabase.paidToDAO()
    }

    @Singleton
    @Provides
    fun provideSuggestionSyncApi(dataStore: DataStore<Preferences>): SuggestionSyncAPI {
        return SuggestionSyncAPI(dataStore)
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
