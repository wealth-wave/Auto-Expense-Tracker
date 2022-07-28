package app.expense.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.expense.contract.TransactionType

@Entity(tableName = "transaction")
class TransactionDTO(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "from_id") val fromId: String,
    @ColumnInfo(name = "from_name") val fromName: String,
    @ColumnInfo(name = "to_id") val toId: String,
    @ColumnInfo(name = "to_name") val toName: String,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "type") val type: TransactionType,
    @ColumnInfo(name = "reference_id") val referenceId: String
) {
}