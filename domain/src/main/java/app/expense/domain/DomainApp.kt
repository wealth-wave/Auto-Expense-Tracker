package app.expense.domain

import android.app.Application
import app.expense.di.ApiServiceLocator
import app.expense.domain.smsTemplate.SMSTemplateMatcher
import app.expense.domain.smsTemplate.SMSTemplateProvider
import app.expense.domain.transaction.TransactionDetector
import app.expense.domain.transaction.TransactionSyncService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class DomainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DomainApp)
            module {
                single { SMSTemplateProvider() }
                single { SMSTemplateMatcher() }
                single { TransactionDetector(get(), get()) }
                single {
                    TransactionSyncService(
                        ApiServiceLocator.transactionSyncAPI,
                        ApiServiceLocator.smsReadApi,
                        get()
                    )
                }
            }
        }
    }
}