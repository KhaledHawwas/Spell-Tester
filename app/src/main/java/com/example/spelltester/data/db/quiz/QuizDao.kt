package com.example.spelltester.data.db.quiz

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spelltester.data.db.attempt.Attempt

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(quiz: Quiz)

    @Query("SELECT * FROM quiz")
    fun getAllQuiz(): LiveData<List<Quiz>>

    @Delete
    fun deleteQuiz(quiz: Quiz)

    @Query("SELECT * FROM attempts WHERE quizId = :quizId")
     fun getAttemptsByQuizId(quizId: Int): List<Attempt>

}