package com.example.spelltester.data.db.quiz

import androidx.room.*

class IntArrayConverter {
    @TypeConverter
    fun fromIntArray(value: IntArray): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun toIntArray(value: String): IntArray {
        return value.split(",").map { if (it.isBlank()) 0 else it.toInt() }.toIntArray()
    }
}