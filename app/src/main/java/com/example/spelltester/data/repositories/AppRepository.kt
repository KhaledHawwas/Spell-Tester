package com.example.spelltester.data.repositories

import com.example.spelltester.data.db.*
import com.example.spelltester.data.db.attempt.*
import com.example.spelltester.data.db.quiz.*
import com.example.spelltester.data.db.user.*
import com.example.spelltester.data.db.word.*

class AppRepository(
    private val db: AppDatabase
) : Repository {
    //words
    override fun getWordByWordId(wordId: Int): Word? = db.wordDao().getWordByWordId(wordId)
    override fun deleteWord(word: Word) = db.wordDao().deleteWord(word)
    override fun upsert(word: Word) = db.wordDao().upsert(word)
    override fun getWordsByWordsId(wordsId: IntArray) = db.wordDao().getWordsByWordsId(wordsId)

    //users
    override fun upsert(user: User) = db.userDao().upsert(user)
    override fun getAllUsers() = db.userDao().getAllUsers()
    override fun getUserByUsername(username: String): User? =
        db.userDao().getUserByUsername(username)

    //attempts
    override fun getAttemptsByUserId(userId: Int) = db.userDao().getAttemptsByUserId(userId)
    override fun getAttemptsByQuizId(quizId: Int) = db.quizDao().getAttemptsByQuizId(quizId)
    override fun getAllAttempt() = db.attemptDao().getAttempt()
    override fun upsert(attempt: Attempt) = db.attemptDao().upsert(attempt)
    override fun deleteAttempt(attempt: Attempt) = db.attemptDao().deleteAttempt(attempt)
    override fun getAttemptByWordId(wordId: Int) =
        db.attemptDao().getAttemptByWordId(wordId)

    //quiz's
    override fun upsert(quiz: Quiz) = db.quizDao().upsert(quiz)
    override fun delete(quiz: Quiz) = db.quizDao().deleteQuiz(quiz)
    override fun getAllQuiz() = db.quizDao().getAllQuiz()
    override fun getQuizByQuizId(quizId: Int): Quiz? = db.quizDao().getQuizByQuizId(quizId)


    companion object {
        @Volatile
        private var instance: AppRepository? = null
        private val LOCK = Any()

        operator fun invoke(database: AppDatabase) = instance
            ?: synchronized(LOCK) {
                instance ?: AppRepository(database).also { instance = it }
            }

        fun getInstance(): AppRepository = instance!!
    }
}