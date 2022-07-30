package app.expense

import android.app.Application
import app.expense.api.SMSReadAPI
import app.expense.api.TransactionSyncAPI
import app.expense.db.DaoProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ApiApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ApiApp)
            module {
                single { androidContext() }
                single { androidContext().getSharedPreferences("SESSION", MODE_PRIVATE) }
                single { DaoProvider(androidContext()).getTransactionDao() }
                single { androidContext().contentResolver }
                single { TransactionSyncAPI(get(), get()) }
                single { SMSReadAPI(get()) }
            }
        }
    }
}