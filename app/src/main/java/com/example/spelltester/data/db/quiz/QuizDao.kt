package com.example.spelltester.data.db.quiz

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(quiz: Quiz)

    @Query("SELECT * FROM quiz")
    fun getAllQuiz(): List<Quiz>

    @Delete
    fun deleteQuiz(quiz: Quiz)

}