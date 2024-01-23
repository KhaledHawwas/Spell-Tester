package com.example.spelltester.data.db

import com.example.spelltester.data.db.attempt.Attempt
import com.example.spelltester.data.db.quiz.Quiz
import com.example.spelltester.data.db.word.Word
import com.example.spelltester.data.repositories.AppRepository
import com.example.spelltester.data.repositories.Repository
import java.util.concurrent.TimeUnit

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
        if (attempt.points>0) progress.correct++
        else if (attempt.points==0f) progress.unanswered++
        else progress.incorrect++
    }
    
    return progress
}
