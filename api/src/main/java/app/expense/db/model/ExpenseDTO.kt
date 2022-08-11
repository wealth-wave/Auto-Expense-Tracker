package app.expense.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DBModel to hold Expense Information.
 */
@Entity(tableName = "expense")
data class ExpenseDTO(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "categories") val categories: List<String>,
    @ColumnInfo(name = "paid_to") val paidTo: String?,
    @ColumnInfo(name = "time") val time: Long
)
