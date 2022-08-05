package app.expense.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggestion")
class SuggestionDTO(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "paid_to") val paidTo: String?,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "reference_message") val referenceMessage: String,
    @ColumnInfo(name = "reference_message_sender") val referenceMessageSender: String
) {
}