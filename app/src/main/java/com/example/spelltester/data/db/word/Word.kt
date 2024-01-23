package com.example.spelltester.data.db.word

import androidx.room.*

@Entity(tableName = "words",)
data class Word(

    val englishWord: String,
    val arabicWord: String,
    val quizId: Int?=null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wordId")
    var wordId: Int=1
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