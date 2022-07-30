package app.expense.tracker.services.di

import app.expense.domain.di.DomainServiceLocator
import app.expense.domain.transaction.TransactionSyncService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceDIModule {

    @Singleton
    @Provides
    fun provideTransactionSyncService(): TransactionSyncService {
        TODO() //return DomainServiceLocator.transactionSyncService
    }
}