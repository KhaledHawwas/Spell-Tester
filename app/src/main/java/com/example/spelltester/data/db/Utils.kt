package com.example.spelltester.data.db

import com.example.spelltester.data.db.attempt.*
import com.example.spelltester.data.db.quiz.*
import com.example.spelltester.data.db.word.*
import com.example.spelltester.data.repositories.*
import java.util.concurrent.*

private const val TAG = "Utils"
fun Attempt.calculateRate( word: Word): Float {
    //  = AppRepository.getInstance().getWordByWordId(wordId)///TODO
    var timePoints: Int //TODO
    val diff: Long = System.currentTimeMillis() - lastAttempt
    val hours = TimeUnit.MILLISECONDS.toHours(diff).toInt()

    //introduce local final variable for constant 4 and 6.25

    var totalRate: Float = (points + 4) * 1.25f * 6 + /*timePoints*/ +word.difficultyLevel()
    totalRate = if (totalRate >= 6.25) totalRate else 6.25f
    return 4 / (totalRate / 6.25f)
}
data class Progress(
    var correct: Int,
    var incorrect: Int,
    var unanswered: Int
)
fun Quiz.calculateProgress(repository: Repository=AppRepository.getInstance())
: Progress {
    val attempts = repository.getAttemptsByQuizId(quizId)
    val progress = Progress(0, 0, 0)
    for (attempt in attempts) {
        when {
            attempt.points >= 2f -> progress.correct++
            attempt.points <= -2f -> progress.incorrect++
            else -> progress.unanswered++
        }
    }

    return progress
}

fun minDistance(word1: String, word2: String): Int {
    val dp = Array(word1.length + 1) {
        IntArray(
            word2.length + 1
        )
    }
    for (i in 0..word1.length) {
        for (j in 0..word2.length) {
            if (i == 0 || j == 0) dp[i][j] = 0 else dp[i][j] =
                if (word1[i - 1] == word2[j - 1]) dp[i - 1][j - 1] + 1 else Math.max(
                    dp[i - 1][j], dp[i][j - 1]
                )
        }
    }
    val value = dp[word1.length][word2.length]
    return word1.length - value + word2.length - value
}