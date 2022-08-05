package app.expense.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import app.expense.tracker.services.SMSSyncWorker
import app.expense.tracker.ui.theme.AutoExpenseTrackerTheme
import app.expense.tracker.ui.views.dashboard.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint

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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    DashboardScreen(navController)
                }
            }
        }
    }

    private fun syncSMS() {
        val smsSyncRequest = OneTimeWorkRequestBuilder<SMSSyncWorker>()
            .build()
        WorkManager.getInstance(this).enqueue(smsSyncRequest)
    }
}