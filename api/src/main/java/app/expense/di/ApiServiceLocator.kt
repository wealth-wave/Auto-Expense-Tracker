package app.expense.di

import app.expense.api.SMSReadAPI
import app.expense.api.TransactionSyncAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ApiServiceLocator : KoinComponent {
    val transactionSyncAPI by inject<TransactionSyncAPI>()
    val smsReadApi by inject<SMSReadAPI>()
}