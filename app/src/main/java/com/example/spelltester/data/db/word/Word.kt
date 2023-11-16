package com.example.spelltester.data.db.word

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word (

    val englishWord: String,
    val arabicWord: String,
    @PrimaryKey(autoGenerate = true)
    var wordId: Int=0
)
{
    fun difficultyLevel(): Int {
        return when (englishWord.length) {
            1, 2, 3, 4 -> 20
            5 -> 15
            6, 7 -> 10
            8 -> 5
            else -> 0
        }
    }
}