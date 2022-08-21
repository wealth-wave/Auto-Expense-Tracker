package app.expense.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import app.expense.tracker.services.SMSSyncWorker
import app.expense.tracker.ui.nav.HomeNavigation
import app.expense.tracker.ui.theme.AutoExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachUI()
        syncSMS()
    }

    private fun attachUI() {
        setContent {
            AutoExpenseTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeNavigation()
                }
            }
        }
    }

    private fun syncSMS() {
        val workManager =
            WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            "SMS_SYNC",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequest.Builder(
                SMSSyncWorker::class.java,
                Duration.ofMinutes(15),
                Duration.ofMinutes(5)
            ).build()
        )
    }
}
