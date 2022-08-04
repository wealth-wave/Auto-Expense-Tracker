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

    @Query("SELECT * FROM `suggestion` WHERE time > :upTo")
    fun fetchSuggestions(upTo: Long): Flow<List<SuggestionDTO>>
}