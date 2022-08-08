package app.expense.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "paid_to", indices = [Index(value = ["name"], unique = true)])
data class PaidToDTO(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo(name = "name") val name: String,
)
