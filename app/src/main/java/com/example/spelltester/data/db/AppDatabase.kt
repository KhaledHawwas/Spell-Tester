package com.example.spelltester.data.db

import android.content.*
import androidx.room.*
import com.example.spelltester.data.db.user.*
import com.example.spelltester.data.db.word.*
import com.example.spelltester.data.db.attempt.*
import com.example.spelltester.data.db.quiz.*


@Database(
    entities = [User::class, Word::class, Attempt::class, Quiz::class],
    version = 8,
)
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
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}