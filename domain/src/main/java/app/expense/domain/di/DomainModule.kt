package app.expense.domain.di

import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.api.SuggestionsReadAPI
import app.expense.domain.SuggestionFetchService
import app.expense.domain.smsTemplate.SMSTemplateMatcher
import app.expense.domain.smsTemplate.SMSTemplateProvider
import app.expense.domain.suggestion.SuggestionSyncService
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
    ): SuggestionSyncService {
        return SuggestionSyncService(suggestionSyncAPI, smsReadAPI, suggestionDetector)
    }

    @Provides
    fun suggestionFetchService(suggestionsReadAPI: SuggestionsReadAPI): SuggestionFetchService {
        return SuggestionFetchService(suggestionsReadAPI)
    }
}