package app.expense.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.expense.model.PaidToDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface PaidToDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paidTo: PaidToDTO)

    @Query("SELECT * FROM `paid_to` WHERE name LIKE '%'|| :name || '%' LIMIT 3")
    fun fetchPaidTo(name: String): Flow<List<PaidToDTO>>
}
