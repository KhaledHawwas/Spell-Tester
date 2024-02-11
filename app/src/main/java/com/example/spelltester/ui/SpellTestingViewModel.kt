package com.example.spelltester.ui

import androidx.lifecycle.*
import com.example.spelltester.*
import com.example.spelltester.data.db.*
import com.example.spelltester.data.db.attempt.*
import com.example.spelltester.data.db.word.*
import com.example.spelltester.data.repositories.*
import kotlin.math.*
import kotlin.random.*

const val TAG_S_T_V_M = "SpellTestingViewModel"

class SpellTestingViewModel(private val repository: Repository, quizId: Int) : ViewModel() {
    enum class Status {
        ANSWERING, SHOWING, DONE, ERROR
    }

    enum class Message {
        CORRECT, FINISH, EMPTY, ERROR
    }

    var attempts: List<Attempt> = mutableListOf()
    var attempt: Attempt? = null
    var gainedPoints = 0f
    var status: Status = Status.ANSWERING
    var errorMessage: Int = 0

    init {
        if (quizId == -1) {
            status = Status.ERROR
            errorMessage = R.string.error_finding_quiz
        } else {

            val words = repository.getWordsByWordsId(
                repository.getQuizByQuizId(quizId)?.wordsId ?: IntArray(0)
            )
            words.forEach { word ->
                Attempt.addIfNotExist(word.wordId, quizId)
            }
            attempts = repository.getAttemptsByQuizId(quizId)


            attempt = getNextAttempt()
            if (attempt == null) {
                status = Status.ERROR
                errorMessage = R.string.error_finding_word
            }
        }
    }

    private fun getNextAttempt(): Attempt? {
        // choose word in words with ratio
        var filteredAttempts = attempts.filter { it.points != Attempt.MAX_POINT }
        if (filteredAttempts.isEmpty()) return null
        val weights = filteredAttempts.map { (it.points * .5 + 4) }
        val totalWeight = weights.sum()
        val random = Random.nextFloat() * totalWeight
        var i = 0
        var sum = weights[i]
        while (sum < random) {
            i++
            sum += weights[i]
        }
        return filteredAttempts[i]
    }

    fun answer(userInput: String?): Float {
        val userAnswer: String = formatWord(userInput ?: "")
        val thisWord: Word = attempt?.word ?: return Float.NaN
        val correctAnswer: String = thisWord.englishWord

        val fault: Int = minDistance(correctAnswer, userAnswer)
        var point = when {
            fault == 0 -> 2
            correctAnswer.length / 3 >= fault -> 1
            correctAnswer.length / 2 >= fault -> -1
            else -> -2
        }
        //   thisWord.answer(point)
        return point.toFloat()
    }

    fun processClicking(text: String?): Message {
        return when (status) {
            Status.ANSWERING -> {
                if (attempt == null) {
                    status = Status.ERROR
                    errorMessage = R.string.error_finding_word
                    Message.ERROR
                }
                attempt?.let {
                    if (text?.isBlank() != false) {
                        //  errorMessage=R.string.please_enter_a_word TODO: add this message to the UI
                        Message.EMPTY
                    }
                    gainedPoints = answer(text)
                    if (gainedPoints == Float.NaN) {
                        errorMessage = R.string.error_finding_word
                        status = Status.ERROR
                        Message.ERROR
                    }
                    it.points = min(Attempt.MAX_POINT, it.points + gainedPoints)
                    it.points = max(Attempt.MIN_POINT, it.points)
                    it.lastAttempt = System.currentTimeMillis()
                    repository.upsert(it)

                }
                status = Status.SHOWING
                Message.CORRECT
            }

            Status.SHOWING -> {
                attempt = getNextAttempt()
                status = if (attempt == null) Status.DONE else Status.ANSWERING
                Message.CORRECT
            }

            Status.DONE, Status.ERROR -> {
                Message.FINISH
            }

        }
    }

}