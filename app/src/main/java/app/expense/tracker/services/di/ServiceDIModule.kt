package app.expense.tracker.services.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ServiceDIModule {

//    @Singleton
//    @Provides
//    fun provideTransactionSyncService(): TransactionSyncService {
//        TODO() //return DomainServiceLocator.transactionSyncService
//    }
}