package com.example.spelltester.data.db.attempt

import androidx.room.*
import com.example.spelltester.data.db.word.*
import com.example.spelltester.data.repositories.*

@Entity(tableName = "attempts", )
data class Attempt(
    val userId: Int,
    var quizId: Int,
    var wordId: Int,
    var points: Float,
    var lastAttempt: Long,
    @PrimaryKey(autoGenerate = true)
    var attemptId: Int = 0,
) {
    @delegate:Transient
    val word: Word? by lazy { AppRepository.getInstance().getWordByWordId(wordId) }
    companion object {
        const val MAX_POINT = 4.0f
        const val MIN_POINT = -4.0f
        fun addIfNotExist(
            wordId: Int,
            quizId: Int,
            repository: Repository = AppRepository.getInstance()
        ) {
            if (repository.getAttemptByWordId(wordId) == null) {
                repository.upsert(Attempt(0, quizId, wordId, 0f, 0L))
            }
        }

    }

    fun isAnswered(): Boolean {
        return lastAttempt != 0L
    }


}