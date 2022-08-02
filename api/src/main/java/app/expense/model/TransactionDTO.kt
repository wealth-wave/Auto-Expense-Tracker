package app.expense.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.expense.contract.TransactionType

@Entity(tableName = "transaction")
class TransactionDTO(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "from_name") val fromName: String?,
    @ColumnInfo(name = "to_name") val toName: String?,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "type") val type: TransactionType,
    @ColumnInfo(name = "reference_id") val referenceId: String,
    @ColumnInfo(name = "reference_message") val referenceMessage: String,
    @ColumnInfo(name = "reference_message_sender") val referenceMessageSender: String
) {
}