package app.expense.db

import androidx.room.TypeConverter

/**
 * Type Converters to convert to and from between Model Class and DB String.
 *
 * Since Room DB Cannot store Model class, we need to convert the model class to string to save and re-construct it on fetch.
 */
class ModelConverter {

    @TypeConverter
    fun listToString(value: List<String>): String = value.joinToString(",")

    @TypeConverter
    fun stringToList(value: String): List<String> = value.split(",")
}
