package app.expense.domain.di

import app.expense.api.SMSReadAPI
import app.expense.api.TransactionSyncAPI
import app.expense.api.TransactionsReadAPI
import app.expense.domain.TransactionFetchService
import app.expense.domain.smsTemplate.SMSTemplateMatcher
import app.expense.domain.smsTemplate.SMSTemplateProvider
import app.expense.domain.transaction.TransactionSyncService
import app.expense.domain.transaction.detector.TransactionDetector
import app.expense.domain.transaction.detector.TransactionDetectorByParserImpl
import app.expense.domain.transaction.detector.TransactionParserHelper
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
    fun provideTransactionParserHelper(): TransactionParserHelper {
        return TransactionParserHelper()
    }

//    @Provides
//    fun provideTransactionDetector(
//        smsTemplateProvider: SMSTemplateProvider,
//        smsTemplateMatcher: SMSTemplateMatcher
//    ): TransactionDetector {
//        return TransactionDetectorByTemplateImpl(smsTemplateProvider, smsTemplateMatcher)
//    }

    @Provides
    fun provideTransactionDetector(transactionParserHelper: TransactionParserHelper): TransactionDetector {
        return TransactionDetectorByParserImpl(transactionParserHelper)
    }

    @Provides
    fun transactionSyncService(
        transactionSyncAPI: TransactionSyncAPI,
        smsReadAPI: SMSReadAPI,
        transactionDetector: TransactionDetector
    ): TransactionSyncService {
        return TransactionSyncService(transactionSyncAPI, smsReadAPI, transactionDetector)
    }

    @Provides
    fun transactionFetchService(transactionsReadAPI: TransactionsReadAPI): TransactionFetchService {
        return TransactionFetchService(transactionsReadAPI)
    }
}