package app.expense.api

import android.content.ContentResolver
import android.net.Uri
import app.expense.contract.SMSMessage

class SMSReadAPI(private val contentResolver: ContentResolver) {

    fun getAllSms(upTo: Long?): List<SMSMessage> {
        val smsMessages = mutableListOf<SMSMessage>()

        val projections = arrayOf("_id", "address", "body", "person", "date")
        val selection = if (upTo != null) "date > ?" else null
        val selectionArgs = if (upTo != null) arrayOf(upTo.toString()) else null
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

            smsMessages.add(SMSMessage(address, body, time))
        }

        cursor.close()

        return smsMessages
    }
}