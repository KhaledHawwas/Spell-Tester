package com.example.spelltester.data.db.attempt

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.spelltester.data.db.word.Word
import com.example.spelltester.data.repositories.AppRepository
import java.util.concurrent.TimeUnit

@Entity(tableName = "attempt")


data class Attempt(

    val userId: Int, // Foreign key referencing the User table
    var points: Float,
    val wordId: Int,
    var lastAttempt: Long,
    @PrimaryKey(autoGenerate = true)
    var attemptId: Int=0,
) {
    companion object {
        val MAX_POINT = 4
        val MIN_POINT = -4
    }

    fun isAnswered(): Boolean {
        return lastAttempt != 0L
    }
    fun calculateRate(): Float {
        var word: Word=AppRepository.getInstance().getWordByWordId(wordId)///TODO
        var timePoints: Int
        val diff: Long = System.currentTimeMillis() - lastAttempt
        val hours = TimeUnit.MILLISECONDS.toHours(diff).toInt()
        //TODO calculate timePoints
        var totalRate: Float = (points + 4) * 1.25f * 6 + /*timePoints*/ + word.difficultyLevel()
        totalRate = if (totalRate >= 6.25) totalRate else 6.25f
        return 4 / (totalRate / 6.25f)
    }


}