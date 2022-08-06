package app.expense.domain.di

import app.expense.api.ExpenseAPI
import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.api.SuggestionsAPI
import app.expense.domain.expense.AddExpenseUseCase
import app.expense.domain.expense.FetchExpenseUseCase
import app.expense.domain.smsTemplate.SMSTemplateMatcher
import app.expense.domain.smsTemplate.SMSTemplateProvider
import app.expense.domain.suggestion.FetchSuggestionUseCase
import app.expense.domain.suggestion.SyncSuggestionUseCase
import app.expense.domain.suggestion.detector.SuggestionDetector
import app.expense.domain.suggestion.detector.SuggestionDetectorByParserImpl
import app.expense.domain.suggestion.detector.SuggestionParserHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Provides
    fun provideSMSTemplateProvider(): SMSTemplateProvider {
        return SMSTemplateProvider()
    }

    @Provides
    fun provideSMSTemplateMatcher(): SMSTemplateMatcher {
        return SMSTemplateMatcher()
    }

    @Provides
    fun provideSuggestionParserHelper(): SuggestionParserHelper {
        return SuggestionParserHelper()
    }

    @Provides
    fun provideSuggestionDetector(suggestionParserHelper: SuggestionParserHelper): SuggestionDetector {
        return SuggestionDetectorByParserImpl(suggestionParserHelper)
    }

    @Provides
    fun suggestionSyncService(
        suggestionSyncAPI: SuggestionSyncAPI,
        smsReadAPI: SMSReadAPI,
        suggestionDetector: SuggestionDetector
    ): SyncSuggestionUseCase {
        return SyncSuggestionUseCase(suggestionSyncAPI, smsReadAPI, suggestionDetector)
    }

    @Provides
    fun suggestionFetchUseCase(suggestionsAPI: SuggestionsAPI): FetchSuggestionUseCase {
        return FetchSuggestionUseCase(suggestionsAPI)
    }

    @Provides
    fun fetchExpenseUseCase(expenseAPI: ExpenseAPI): FetchExpenseUseCase {
        return FetchExpenseUseCase(expenseAPI)
    }

    @Provides
    fun addExpenseUseCase(expenseAPI: ExpenseAPI): AddExpenseUseCase {
        return AddExpenseUseCase(expenseAPI)
    }
}