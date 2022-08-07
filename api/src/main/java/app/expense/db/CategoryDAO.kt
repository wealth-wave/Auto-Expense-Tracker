package app.expense.db

import android.database.sqlite.SQLiteDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.expense.model.CategoryDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {

    @Insert(onConflict = SQLiteDatabase.CONFLICT_ABORT)
    suspend fun insert(category: CategoryDTO)

    @Query("SELECT * FROM `category` WHERE name LIKE '%'|| :name || '%' LIMIT 3")
    fun fetchCategories(name: String): Flow<List<CategoryDTO>>
}