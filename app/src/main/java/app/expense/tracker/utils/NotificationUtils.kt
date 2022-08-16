package app.expense.tracker.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
        notificationManager.notify(suggestionId.toInt(), buildExpenseDetectedNotification(amount))
    }

    private fun buildExpenseDetectedNotification(amount: Double): Notification {
        return NotificationCompat.Builder(context, EXPENSE_DETECTED_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.expense_detected_title))
            .setContentText(
                context.getString(
                    R.string.expense_detected_desc,
                    NumberFormat.getCurrencyInstance().format(amount)
                )
            )
//            .setContentIntent(
//                PendingIntent.getActivity()
//            )
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
