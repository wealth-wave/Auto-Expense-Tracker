package app.expense.tracker.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import app.expense.tracker.MainActivity
import app.expense.tracker.R
import java.text.NumberFormat

class NotificationUtils(
    private val context: Context,
    private val notificationManager: NotificationManagerCompat
) {

    fun showNewSuggestionNotification(suggestionId: Long, amount: Double) {
        createNotificationChannel(
            EXPENSE_DETECTED_CHANNEL_ID,
            context.getString(R.string.expense_detection_notif_channel_name),
            context.getString(
                R.string.expense_detection_notif_channel_desc
            )

        )
        notificationManager.notify(
            suggestionId.toInt(),
            buildExpenseDetectedNotification(suggestionId, amount)
        )
    }

    private fun buildExpenseDetectedNotification(suggestionId: Long, amount: Double): Notification {
        return NotificationCompat.Builder(context, EXPENSE_DETECTED_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.expense_detected_title))
            .setContentText(
                context.getString(
                    R.string.expense_detected_desc,
                    NumberFormat.getCurrencyInstance().format(amount)
                )
            )
            .setContentIntent(
                TaskStackBuilder.create(context).run {
                    addNextIntentWithParentStack(
                        Intent(
                            Intent.ACTION_VIEW,
                            "expense://home".toUri(),
                            context,
                            MainActivity::class.java
                        )
                    )
                    getPendingIntent(suggestionId.toInt(), PendingIntent.FLAG_UPDATE_CURRENT)
                }
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel(id: String, name: String, description: String) {
        notificationManager.createNotificationChannel(
            NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW).also {
                it.description = description
            }
        )
    }

    companion object {
        private const val EXPENSE_DETECTED_CHANNEL_ID = "expense_detected"
    }
}
