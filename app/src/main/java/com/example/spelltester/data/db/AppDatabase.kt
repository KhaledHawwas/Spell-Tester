package com.example.spelltester.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spelltester.data.db.user.User
import com.example.spelltester.data.db.user.UserDao
import com.example.spelltester.data.db.word.Word
import com.example.spelltester.data.db.word.WordDao
import com.example.spelltester.data.db.attempt.Attempt
import com.example.spelltester.data.db.attempt.AttemptDao
import com.example.spelltester.data.db.quiz.Quiz
import com.example.spelltester.data.db.quiz.QuizDao

@Database(entities = [User::class, Word::class, Attempt::class,Quiz::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun wordDao(): WordDao
    abstract fun attemptDao(): AttemptDao
    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(
                        context
                    ).also { instance = it }
            }

        fun getInstance() = instance!!
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "SpellTestDatabase.db"
            ).allowMainThreadQueries().build()
    }
}