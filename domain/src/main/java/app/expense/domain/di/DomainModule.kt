package app.expense.domain.di

import app.expense.api.CategoryAPI
import app.expense.api.ExpenseAPI
import app.expense.api.PaidToAPI
import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.api.SuggestionsAPI
import app.expense.domain.categories.FetchCategoriesUseCase
import app.expense.domain.expense.mappers.ExpenseDataMapper
import app.expense.domain.expense.usecases.AddExpenseUseCase
import app.expense.domain.expense.usecases.DeleteExpenseUseCase
import app.expense.domain.expense.usecases.FetchExpenseUseCase
import app.expense.domain.paidTo.FetchPaidToUseCase
import app.expense.domain.suggestion.detector.RegexHelper
import app.expense.domain.suggestion.detector.SuggestionDetector
import app.expense.domain.suggestion.detector.SuggestionDetectorImpl
import app.expense.domain.suggestion.mappers.SMSMessageDataMapper
import app.expense.domain.suggestion.mappers.SuggestionDataMapper
import app.expense.domain.suggestion.usecases.DeleteSuggestionUseCase
import app.expense.domain.suggestion.usecases.FetchSuggestionUseCase
import app.expense.domain.suggestion.usecases.SyncSuggestionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Provides
    fun provideSuggestionParserHelper(): RegexHelper {
        return RegexHelper()
    }

    @Provides
    fun provideExpenseDataMapper(): ExpenseDataMapper {
        return ExpenseDataMapper()
    }

    @Provides
    fun provideSuggestionDataMapper(): SuggestionDataMapper {
        return SuggestionDataMapper()
    }

    @Provides
    fun provideSMSDataMapper(): SMSMessageDataMapper {
        return SMSMessageDataMapper()
    }

    @Provides
    fun provideSuggestionDetector(regexHelper: RegexHelper): SuggestionDetector {
        return SuggestionDetectorImpl(regexHelper)
    }

    @Provides
    fun suggestionSyncService(
        suggestionSyncAPI: SuggestionSyncAPI,
        suggestionsAPI: SuggestionsAPI,
        smsReadAPI: SMSReadAPI,
        suggestionDetector: SuggestionDetector,
        dataMapper: SMSMessageDataMapper
    ): SyncSuggestionUseCase {
        return SyncSuggestionUseCase(
            suggestionSyncAPI,
            suggestionsAPI,
            smsReadAPI,
            suggestionDetector,
            dataMapper
        )
    }

    @Provides
    fun fetchSuggestionUseCase(
        suggestionsAPI: SuggestionsAPI,
        dataMapper: SuggestionDataMapper
    ): FetchSuggestionUseCase {
        return FetchSuggestionUseCase(suggestionsAPI, dataMapper)
    }

    @Provides
    fun fetchExpenseUseCase(
        expenseAPI: ExpenseAPI,
        dataMapper: ExpenseDataMapper
    ): FetchExpenseUseCase {
        return FetchExpenseUseCase(expenseAPI, dataMapper)
    }

    @Provides
    fun addExpenseUseCase(
        expenseAPI: ExpenseAPI,
        suggestionsAPI: SuggestionsAPI,
        categoryAPI: CategoryAPI,
        paidToAPI: PaidToAPI
    ): AddExpenseUseCase {
        return AddExpenseUseCase(expenseAPI, suggestionsAPI, categoryAPI, paidToAPI)
    }

    @Provides
    fun getCategoriesUseCase(categoryAPI: CategoryAPI) = FetchCategoriesUseCase(categoryAPI)

    @Provides
    fun getPaidToUseCase(paidToAPI: PaidToAPI) = FetchPaidToUseCase(paidToAPI)

    @Provides
    fun getDeleteExpenseUseCase(expenseAPI: ExpenseAPI) = DeleteExpenseUseCase(expenseAPI)

    @Provides
    fun getDeleteSuggestionUseCase(suggestionsAPI: SuggestionsAPI) =
        DeleteSuggestionUseCase(suggestionsAPI)
}
