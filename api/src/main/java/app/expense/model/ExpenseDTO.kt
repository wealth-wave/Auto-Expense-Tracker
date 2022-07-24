package app.expense.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ExpenseDTO(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "merchant_id") val merchantId: String,
    @ColumnInfo(name = "merchant_name") val merchantName: String,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "reference_id") val referenceId: String,
    @ColumnInfo(name = "account_id") val accountId: String,
    @ColumnInfo(name = "account_name") val accountName: String
) {
}