package com.example.spelltester.data.repositories

import androidx.lifecycle.LiveData
import com.example.spelltester.data.db.attempt.Attempt
import com.example.spelltester.data.db.quiz.*
import com.example.spelltester.data.db.user.*
import com.example.spelltester.data.db.word.*

interface Repository {
    fun getWordByWordId(wordId: Int): Word
    fun deleteWord(word: Word)
    fun upsert(word: Word)
    fun getWordByQuizId(quizId: Int): List<Word>
    //users
    fun upsert(user: User)
    fun getAllUsers(): LiveData<List<User>>
    fun getUserByUsername(username: String): User

    //attempts
    fun getAttemptsByUserId(userId: Int): List<Attempt>
    fun getAttemptsByQuizId(quizId: Int): List<Attempt>
    fun getAllAttempt(): List<Attempt>
    fun upsert(attempt: Attempt)
    fun deleteAttempt(attempt: Attempt)
    fun getAttemptByWordId(wordId: Int): Attempt

    //quiz's
    fun upsert(quiz: Quiz)
    fun delete(quiz: Quiz)
    fun getAllQuiz(): LiveData<List<Quiz>>

}