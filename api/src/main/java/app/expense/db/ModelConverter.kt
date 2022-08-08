package app.expense.db

import androidx.room.TypeConverter

class ModelConverter {

    @TypeConverter
    fun listToString(value: List<String>): String = value.joinToString(",")

    @TypeConverter
    fun stringToList(value: String): List<String> = value.split(",")
}
