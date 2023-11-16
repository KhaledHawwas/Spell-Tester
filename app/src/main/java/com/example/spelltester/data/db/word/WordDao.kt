package com.example.spelltester.data.db.word

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsert(word: Word)

    @Query("SELECT * FROM words")
     fun getAllWords(): List<Word>

    @Delete
     fun deleteWord(word: Word)

    @Query("SELECT * FROM words WHERE wordId = :wordId")
    fun getWordByWordId(wordId: Int): Word

}