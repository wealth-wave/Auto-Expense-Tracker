package app.expense.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * DB Model to hold paidTo/Merchant names added in Expenses.
 */
@Entity(tableName = "paid_to", indices = [Index(value = ["name"], unique = true)])
data class PaidToDTO(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo(name = "name") val name: String,
)
