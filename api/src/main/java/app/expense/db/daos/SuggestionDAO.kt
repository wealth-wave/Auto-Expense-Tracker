package app.expense.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.expense.db.model.SuggestionDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface SuggestionDAO {

    @Insert
    suspend fun insertAll(suggestions: List<SuggestionDTO>)

    @Query("SELECT * FROM `suggestion` WHERE time > :from AND time < :to ORDER BY time DESC LIMIT 100")
    fun fetchSuggestions(from: Long, to: Long): Flow<List<SuggestionDTO>>

    @Query("SELECT * FROM `suggestion` WHERE id IN (:ids)")
    fun fetchSuggestions(ids: Array<Long>): Flow<List<SuggestionDTO>>

    @Query("SELECT * FROM `suggestion` WHERE time > :from ORDER BY time DESC LIMIT 100")
    fun fetchSuggestions(from: Long): Flow<List<SuggestionDTO>>

    @Query("SELECT * FROM `suggestion` WHERE id = :id")
    fun fetchSuggestion(id: Long): Flow<SuggestionDTO>

    @Query("DELETE FROM `suggestion` WHERE id = :id")
    suspend fun deleteSuggestionById(id: Long)
}
