package com.example.spelltester.data.db.attempt

import androidx.room.*
import com.example.spelltester.data.db.word.*
import com.example.spelltester.data.repositories.*

@Entity(tableName = "attempts", )
data class Attempt(
    @PrimaryKey(autoGenerate = true)
    var attemptId: Int = 0,
    val userId: Int,
    var quizId: Int,
    var wordId: Int,
    var points: Float,
    var lastAttempt: Long,
) {
    @delegate:Transient
    val word: Word by lazy {   AppRepository.getInstance().getWordByWordId(wordId)}
    companion object {
        const val MAX_POINT = 4
        const val MIN_POINT = -4
    }

    fun isAnswered(): Boolean {
        return lastAttempt != 0L
    }





}