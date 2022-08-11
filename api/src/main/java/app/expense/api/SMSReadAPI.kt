package app.expense.api

import android.content.ContentResolver
import android.net.Uri
import app.expense.dtos.SMSMessageDTO

/**
 * API to get SMS from Android DB via contentResolver
 */
class SMSReadAPI(private val contentResolver: ContentResolver) {

    /**
     * Get all SMS from Android DB
     *
     * @param from: Oldest time to fetch SMS
     */
    fun getAllSms(from: Long?): List<SMSMessageDTO> {
        val smsMessages = mutableListOf<SMSMessageDTO>()

        val projections = arrayOf("_id", "address", "body", "person", "date")
        val selection = if (from != null) "date > ?" else null
        val selectionArgs = if (from != null) arrayOf(from.toString()) else null
        val sortOrder = "date DESC"

        val cursor = contentResolver.query(
            Uri.parse("content://sms/inbox"),
            projections,
            selection,
            selectionArgs,
            sortOrder
        ) ?: return emptyList()

        while (cursor.moveToNext()) {
            val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
            val body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
            val time = cursor.getLong(cursor.getColumnIndexOrThrow("date"))

            smsMessages.add(SMSMessageDTO(address, body, time))
        }

        cursor.close()

        return smsMessages
    }
}
