package com.example.spelltester.data.repositories

import com.example.spelltester.data.db.AppDatabase
import com.example.spelltester.data.db.user.User
import com.example.spelltester.data.db.word.Word
import com.example.spelltester.data.db.attempt.Attempt
import com.example.spelltester.data.db.quiz.Quiz

class AppRepository(
    private val db:AppDatabase
) {
    //words
    fun getWordByWordId(wordId: Int)=db.wordDao().getWordByWordId(wordId)
    fun deleteWord(word: Word)=     db.wordDao().deleteWord(word)
    fun upsert(word: Word)= db.wordDao().upsert(word)

    //users
    fun upsert(user: User)= db.userDao().upsert(user)
    fun getAllUsers() = db.userDao().getAllUsers()
    fun getUserByUsername(username: String)=db.userDao().getUserByUsername(username)

    //attempts
    fun getAttemptsByUserId(userId: Int)=db.userDao().getAttemptsByUserId(userId)
    fun getAllAttempt() = db.attemptDao().getAttempt()
    fun upsert(attempt: Attempt) = db.attemptDao().upsert(attempt)
    fun deleteAttempt(attempt: Attempt) = db.attemptDao().deleteAttempt(attempt)
    fun getAttemptByWordId(wordId: Int) =
    db.attemptDao().getAttemptByWordId(wordId)

    //quiz's
    fun upsert(quiz: Quiz)=db.quizDao().upsert(quiz)
    fun delete(quiz: Quiz)=db.quizDao().deleteQuiz(quiz)
    fun getAllQuiz()=db.quizDao().getAllQuiz()

    companion object {
        @Volatile
        private var instance: AppRepository? = null
        private val LOCK = Any()

        operator fun invoke(database: AppDatabase) = instance
            ?: synchronized(LOCK) {
                instance ?: AppRepository(database).also { instance = it }
            }
        fun getInstance():AppRepository= instance!!
    }
}