package app.expense.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.expense.model.SuggestionDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface SuggestionDAO {

    @Insert
    suspend fun insertAll(suggestions: List<SuggestionDTO>)

    @Query("SELECT * FROM `suggestion` WHERE time < :from AND time > :upTo")
    fun fetchSuggestions(from: Long, upTo: Long): Flow<List<SuggestionDTO>>

    @Query("SELECT * FROM `suggestion` WHERE time > :upTo")
    fun fetchSuggestions(upTo: Long): Flow<List<SuggestionDTO>>

    @Query("SELECT * FROM `suggestion` WHERE id = :id")
    fun fetchSuggestion(id: Long): Flow<SuggestionDTO>

    @Query("DELETE FROM `suggestion` WHERE id = :id")
    suspend fun deleteSuggestionById(id: Long)
}
