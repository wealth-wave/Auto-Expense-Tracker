package app.expense.domain.di

import app.expense.domain.transaction.TransactionSyncService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DomainServiceLocator : KoinComponent {
    val transactionSyncService by inject<TransactionSyncService>()
}